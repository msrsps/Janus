/**
 * 
 */
package org.rs2.janus.archive.net;

import java.nio.ByteBuffer;

import org.rs2.janus.archive.ArchiveFileSystem;

/**
 * Represents requests made for archive files.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveRequest {

	/**
	 * The requested archive.
	 */
	private final String request;

	/**
	 * New instance
	 * 
	 * @param request
	 *            The requests archive.
	 */
	public ArchiveRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Gets the archive from the filesystem.
	 * 
	 * @return The requested archive.
	 */
	public ByteBuffer getResponse() {
		ArchiveFileSystem fileSystem = ArchiveFileSystem.getSingleton();
		if (request.startsWith("crc")) {
			return fileSystem.getCrcTable();
		} else if (request.startsWith("title")) {
			return fileSystem.getFile(0, 1);
		} else if (request.startsWith("config")) {
			return fileSystem.getFile(0, 2);
		} else if (request.startsWith("interface")) {
			return fileSystem.getFile(0, 3);
		} else if (request.startsWith("media")) {
			return fileSystem.getFile(0, 4);
		} else if (request.startsWith("versionlist")) {
			return fileSystem.getFile(0, 5);
		} else if (request.startsWith("textures")) {
			return fileSystem.getFile(0, 6);
		} else if (request.startsWith("wordenc")) {
			return fileSystem.getFile(0, 7);
		} else if (request.startsWith("sounds")) {
			return fileSystem.getFile(0, 8);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArchiveRequest [request=" + request + "]";
	}

}
