/**
 * 
 */
package org.rs2.janus.net.ondemand;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.rs2.janus.archive.ArchiveFileSystem;

/**
 * A worker that executes and responds to {@link OnDemandRequest}s.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class OnDemandWorker implements Runnable, Comparable<OnDemandWorker> {

	/**
	 * The {@link OnDemandRequest} that this worker will respond to.
	 */
	private final OnDemandRequest request;

	/**
	 * The {@link Channel} to respond to.
	 */
	private final Channel channel;

	/**
	 * New instance.
	 * 
	 * @param channel
	 *            The {@link Channel} to respond to.
	 * @param request
	 *            The {@link OnDemandRequest} that this worker will respond to.
	 */
	public OnDemandWorker(Channel channel, OnDemandRequest request) {
		this.request = request;
		this.channel = channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ChannelBuffer fileBuffer = ChannelBuffers.wrappedBuffer(ArchiveFileSystem.getSingleton().getFile(request.getIndexFile(),
				request.getArchiveFile()));
		for (int chunk = 0; fileBuffer.readable(); chunk++) {
			int chunkSize = fileBuffer.readableBytes() > 500 ? 506 : fileBuffer.readableBytes() + 6;
			ChannelBuffer chunkBuffer = ChannelBuffers.buffer(chunkSize);
			chunkBuffer.writeByte(request.getIndexFile() - 1);
			chunkBuffer.writeShort(request.getArchiveFile());
			chunkBuffer.writeShort(fileBuffer.capacity());
			chunkBuffer.writeByte(chunk);
			fileBuffer.readBytes(chunkBuffer);

			channel.write(chunkBuffer);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(OnDemandWorker o) {
		return request.compareTo(o.request);
	}

}
