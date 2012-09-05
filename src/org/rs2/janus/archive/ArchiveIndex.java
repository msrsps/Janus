/**
 * 
 */
package org.rs2.janus.archive;

/**
 * Represents an index that points to a raw archive.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveIndex {

	/**
	 * The index file containing this index.
	 */
	private final int indexFile;

	/**
	 * The archive id.
	 */
	private final int archiveFile;

	/**
	 * The archive size.
	 */
	private final int archiveSize;

	/**
	 * The first block of the archive.
	 */
	private final int archiveBlock;

	/**
	 * @param indexFile
	 * @param archiveFile
	 * @param archiveSize
	 * @param archiveBlock
	 */
	public ArchiveIndex(int indexFile, int archiveFile, int archiveSize, int archiveBlock) {
		super();
		this.indexFile = indexFile;
		this.archiveFile = archiveFile;
		this.archiveSize = archiveSize;
		this.archiveBlock = archiveBlock;
	}

	/**
	 * @return the indexFile
	 */
	public int getIndexFile() {
		return indexFile;
	}

	/**
	 * @return the archiveFile
	 */
	public int getArchiveFile() {
		return archiveFile;
	}

	/**
	 * @return the archiveSize
	 */
	public int getArchiveSize() {
		return archiveSize;
	}

	/**
	 * @return the archiveBlock
	 */
	public int getArchiveBlock() {
		return archiveBlock;
	}

}
