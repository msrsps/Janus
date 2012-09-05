/**
 * 
 */
package org.rs2.janus.archive;

/**
 * Represents an index that points to a raw file.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class fileIndex {

	/**
	 * The cache file containing this index.
	 */
	private final int cache;

	/**
	 * The file id.
	 */
	private final int file;

	/**
	 * The file size.
	 */
	private final int fileSize;

	/**
	 * The first block of the file.
	 */
	private final int fileBlock;

	/**
	 * @param cache
	 * @param file
	 * @param fileSize
	 * @param fileBlock
	 */
	public fileIndex(int cache, int file, int fileSize, int fileBlock) {
		super();
		this.cache = cache;
		this.file = file;
		this.fileSize = fileSize;
		this.fileBlock = fileBlock;
	}

	/**
	 * @return the cache id
	 */
	public int getCache() {
		return cache;
	}

	/**
	 * @return the file id
	 */
	public int getFile() {
		return file;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @return the fileBlock
	 */
	public int getFileBlock() {
		return fileBlock;
	}

}
