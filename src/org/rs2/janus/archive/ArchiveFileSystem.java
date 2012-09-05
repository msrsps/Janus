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
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveFileSystem {

	/**
	 * The singleton.
	 */
	private static final ArchiveFileSystem singleton = new ArchiveFileSystem(JanusProperties.getString("ARCHIVE_FILE_DIRECTORY"));

	/**
	 * The directory of the archives.
	 */
	private final String directory;

	/**
	 * The data file containing the raw archives.
	 */
	private RandomAccessFile dataFile;

	/**
	 * The indexes pointing to the archives.
	 */
	private ArchiveIndex[][] archiveIndexes = new ArchiveIndex[5][];

	/**
	 * The raw archive files.
	 */
	private final ByteBuffer[][] archiveFiles = new ByteBuffer[5][];

	/**
	 * The index files containing indexes pointing to the archives.
	 */
	private RandomAccessFile indexFiles[] = new RandomAccessFile[5];

	/**
	 * Crcs of the archives.
	 */
	private ByteBuffer crcTable;

	/**
	 * 
	 * @param directory
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

	private void load() throws Exception {
		loadArchiveIndexes();
		loadArchiveFiles();
		loadCrcTable();

		// Free up some pointless memory.
		archiveIndexes = null;

		for (RandomAccessFile file : indexFiles)
			file.close();

		indexFiles = null;

		dataFile.close();
		dataFile = null;
	}

	/**
	 * 
	 */
	private void loadArchiveIndexes() {
		for (int file = 0; file < indexFiles.length; file++) {
			ArrayList<ArchiveIndex> indexes = new ArrayList<ArchiveIndex>();
			for (int index = 0;; index++) {
				try {
					byte[] buffer = new byte[6];
					indexFiles[file].seek(index * 6);
					indexFiles[file].readFully(buffer);

					int size = ((buffer[0] & 0xFF) << 16) | ((buffer[1] & 0xFF) << 8) | (buffer[2] & 0xFF);
					int block = ((buffer[3] & 0xFF) << 16) | ((buffer[4] & 0xFF) << 8) | (buffer[5] & 0xFF);

					indexes.add(index, new ArchiveIndex(file, index, size, block));
				} catch (Exception e) {
					break;
				}
			}
			archiveIndexes[file] = indexes.toArray(new ArchiveIndex[indexes.size()]);
		}
	}

	/**
	 * 
	 */
	private void loadArchiveFiles() throws IOException {
		int i = 0;
		for (ArchiveIndex[] indexes : archiveIndexes) {
			ArrayList<ByteBuffer> archives = new ArrayList<ByteBuffer>();
			for (ArchiveIndex index : indexes) {
				ByteBuffer archive = ByteBuffer.allocate(index.getArchiveSize());

				long pos = index.getArchiveBlock() * 520;
				int read = 0;
				int chunks = index.getArchiveSize() / 512;

				if (index.getArchiveSize() % 512 != 0)
					chunks++;

				for (int chunk = 0; chunk < chunks; chunk++) {
					byte[] chunkHeader = new byte[8];
					dataFile.seek(pos);
					dataFile.readFully(chunkHeader);

					int nextArchive = ((chunkHeader[0] & 0xff) << 8) | (chunkHeader[1] & 0xff);
					int curChunk = ((chunkHeader[2] & 0xFF) << 8) | (chunkHeader[3] & 0xFF);
					int nextBlock = ((chunkHeader[4] & 0xFF) << 16) | ((chunkHeader[5] & 0xFF) << 8) | (chunkHeader[6] & 0xFF);
					int nextIndexFile = chunkHeader[7] & 0xFF;

					int payloadSize = index.getArchiveSize() - read > 512 ? 512 : index.getArchiveSize() - read;
					byte[] chunkPayload = new byte[payloadSize];
					dataFile.readFully(chunkPayload);

					archive.put(chunkPayload);
					read += payloadSize;
					pos = nextBlock * 520;
				}

				archive.flip();
				archives.add(index.getArchiveFile(), archive);

			}
			archiveFiles[i++] = archives.toArray(new ByteBuffer[archives.size()]);
		}
	}

	/**
	 * 
	 */
	private void loadCrcTable() {
		// the number of archives
		int archives = archiveFiles[0].length;

		// the hash
		int hash = 1234;

		// the CRCs
		int[] crcs = new int[archives];

		// calculate the CRCs
		CRC32 crc32 = new CRC32();
		for (int i = 1; i < crcs.length; i++) {
			crc32.reset();

			ByteBuffer bb = archiveFiles[0][i].asReadOnlyBuffer();
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
	 * 
	 * @param indexFile
	 * @param archiveFile
	 * @return
	 */
	public ByteBuffer getArchive(int indexFile, int archiveFile) {
		return archiveFiles[indexFile - 1][archiveFile].asReadOnlyBuffer();
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
