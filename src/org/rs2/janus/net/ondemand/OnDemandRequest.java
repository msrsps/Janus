/**
 * 
 */
package org.rs2.janus.net.ondemand;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class OnDemandRequest implements Comparable<OnDemandRequest> {

	/**
	 * 
	 */
	private final int priority;

	/**
	 * 
	 */
	private final int indexFile;

	/**
	 * 
	 */
	private final int archiveFile;

	/**
	 * @param priority
	 * @param indexFile
	 * @param archiveFile
	 */
	public OnDemandRequest(int priority, int indexFile, int archiveFile) {
		this.priority = priority;
		this.indexFile = indexFile;
		this.archiveFile = archiveFile;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(OnDemandRequest o) {
		Integer thisPriority = Integer.valueOf(priority);
		Integer oPriority = Integer.valueOf(o.priority);
		return thisPriority.compareTo(oPriority);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OnDemandRequest [priority=" + priority + ", indexFile=" + indexFile + ", archiveFile=" + archiveFile + "]";
	}

}
