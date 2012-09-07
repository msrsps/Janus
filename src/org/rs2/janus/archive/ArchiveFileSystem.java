/**
 * 
 */
package org.rs2.janus.archive;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.CRC32;

import org.rs2.janus.JanusProperties;

/**
 * A file system responsible for loading files from the archives/cache.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveFileSystem {

	/**
	 * The singleton.
	 */
	private static final ArchiveFileSystem singleton = new ArchiveFileSystem(JanusProperties.getString("CACHE_FILE_DIRECTORY"));

	/**
	 * The directory of the cache.
	 */
	private final String directory;

	/**
	 * The data file containing the raw files.
	 */
	private RandomAccessFile dataFile;

	/**
	 * The indexes pointing to the files.
	 */
	private fileIndex[][] fileIndexes = new fileIndex[5][];

	/**
	 * The raw archive files.
	 */
	private final ByteBuffer[][] files = new ByteBuffer[5][];

	/**
	 * The index files containing indexes pointing to the files.
	 */
	private RandomAccessFile indexFiles[] = new RandomAccessFile[5];

	/**
	 * Crcs of the archives.
	 */
	private ByteBuffer crcTable;

	/**
	 * New instance.
	 * 
	 * @param directory
	 *            The directory of the cache.
	 */
	private ArchiveFileSystem(String directory) {
		try {
			// Set directory.
			this.directory = directory;

			// Load data file.
			dataFile = new RandomAccessFile(new File(directory + "main_file_cache.dat"), "r");

			// Load index files.
			for (int index = 0; index < 5; index++)
				indexFiles[index] = new RandomAccessFile(new File(directory + "main_file_cache.idx" + index), "r");

			load();

		} catch (Exception e) {
			throw new RuntimeException("Error loading Archive File System!", e);
		}
	}

	/**
	 * Loads the file system.
	 * 
	 * @throws Exception
	 */
	private void load() throws Exception {
		loadIndexes();
		loadFiles();
		loadCrcTable();

		for (RandomAccessFile file : indexFiles)
			file.close();
		dataFile.close();

		fileIndexes = null;
		indexFiles = null;
		dataFile = null;
	}

	/**
	 * Loads file indexes from index files.
	 */
	private void loadIndexes() {
		for (int file = 0; file < indexFiles.length; file++) {
			ArrayList<fileIndex> indexes = new ArrayList<fileIndex>();
			for (int index = 0;; index++) {
				try {
					byte[] buffer = new byte[6];
					indexFiles[file].seek(index * 6);
					indexFiles[file].readFully(buffer);

					int size = ((buffer[0] & 0xFF) << 16) | ((buffer[1] & 0xFF) << 8) | (buffer[2] & 0xFF);
					int block = ((buffer[3] & 0xFF) << 16) | ((buffer[4] & 0xFF) << 8) | (buffer[5] & 0xFF);

					indexes.add(index, new fileIndex(file, index, size, block));
				} catch (Exception e) {
					break;
				}
			}
			fileIndexes[file] = indexes.toArray(new fileIndex[indexes.size()]);
		}
	}

	/**
	 * Loads files from the data file.
	 */
	private void loadFiles() throws IOException {
		int i = 0;
		for (fileIndex[] indexes : fileIndexes) {
			ArrayList<ByteBuffer> loadedFiles = new ArrayList<ByteBuffer>();
			for (fileIndex index : indexes) {
				ByteBuffer archive = ByteBuffer.allocate(index.getFileSize());

				long pos = index.getFileBlock() * 520;
				int read = 0;
				int chunks = index.getFileSize() / 512;

				if (index.getFileSize() % 512 != 0)
					chunks++;

				for (int chunk = 0; chunk < chunks; chunk++) {
					byte[] chunkHeader = new byte[8];
					dataFile.seek(pos);
					dataFile.readFully(chunkHeader);

					int nextCache = ((chunkHeader[0] & 0xff) << 8) | (chunkHeader[1] & 0xff);
					int curChunk = ((chunkHeader[2] & 0xFF) << 8) | (chunkHeader[3] & 0xFF);
					int nextBlock = ((chunkHeader[4] & 0xFF) << 16) | ((chunkHeader[5] & 0xFF) << 8) | (chunkHeader[6] & 0xFF);
					int nextFile = chunkHeader[7] & 0xFF;

					int payloadSize = index.getFileSize() - read > 512 ? 512 : index.getFileSize() - read;
					byte[] chunkPayload = new byte[payloadSize];
					dataFile.readFully(chunkPayload);

					archive.put(chunkPayload);
					read += payloadSize;
					pos = nextBlock * 520;
				}

				archive.flip();
				loadedFiles.add(index.getFile(), archive);

			}
			files[i++] = loadedFiles.toArray(new ByteBuffer[loadedFiles.size()]);
		}
	}

	/**
	 * Loads the archive Crcs into a table.
	 */
	private void loadCrcTable() {
		// the number of files
		int archives = files[0].length;

		// the hash
		int hash = 1234;

		// the CRCs
		int[] crcs = new int[archives];

		// calculate the CRCs
		CRC32 crc32 = new CRC32();
		for (int i = 1; i < crcs.length; i++) {
			crc32.reset();

			ByteBuffer bb = files[0][i].asReadOnlyBuffer();
			byte[] bytes = new byte[bb.remaining()];
			bb.get(bytes, 0, bytes.length);
			crc32.update(bytes, 0, bytes.length);

			crcs[i] = (int) crc32.getValue();
		}

		// hash the CRCs and place them in the buffer
		ByteBuffer buf = ByteBuffer.allocate(crcs.length * 4 + 4);
		for (int i = 0; i < crcs.length; i++) {
			hash = (hash << 1) + crcs[i];
			buf.putInt(crcs[i]);
		}

		// place the hash into the buffer
		buf.putInt(hash);
		buf.flip();

		crcTable = buf.asReadOnlyBuffer();
	}

	/**
	 * Gets a file from the file system.
	 * 
	 * @param cache
	 *            The cache the file is located in.
	 * @param file
	 *            The file that will be retrieved.
	 * @return The retrieved file.
	 */
	public ByteBuffer getFile(int cache, int file) {
		return files[cache][file].asReadOnlyBuffer();
	}

	/**
	 * 
	 * @return
	 */
	public ByteBuffer getCrcTable() {
		return crcTable.asReadOnlyBuffer();
	}

	/**
	 * @return the singleton
	 */
	public static ArchiveFileSystem getSingleton() {
		return singleton;
	}

}
