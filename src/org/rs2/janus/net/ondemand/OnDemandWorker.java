/**
 * 
 */
package org.rs2.janus.net.ondemand;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.rs2.janus.archive.ArchiveFileSystem;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class OnDemandWorker implements Runnable, Comparable<OnDemandWorker> {

	/**
	 * 
	 */
	private final OnDemandRequest request;

	/**
	 * 
	 */
	private final Channel channel;

	/**
	 * 
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
		System.out.println(request);
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
