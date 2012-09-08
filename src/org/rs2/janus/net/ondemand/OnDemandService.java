/**
 * 
 */
package org.rs2.janus.net.ondemand;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.archive.ArchiveFileSystem;
import org.rs2.janus.net.Service;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class OnDemandService implements Service<OnDemandRequest> {

	/**
	 * The single instance that will handle service all worlds.
	 */
	private static final OnDemandService singleton = new OnDemandService();

	/**
	 * The executor that will execute workers.
	 */
	private final ExecutorService executor = new ThreadPoolExecutor(JanusProperties.getInt("ONDEMAND_WORKER_THREADS"), JanusProperties
			.getInt("ONDEMAND_WORKER_THREADS"), 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());

	/**
	 * New instance.
	 * 
	 * @param channel
	 *            The channel requesting service.
	 * @param request
	 *            The request made.
	 */
	@Override
	public void serviceRequest(ChannelHandlerContext ctx, OnDemandRequest request) throws Exception {
		executor.execute(new Worker(ctx.getChannel(), request));
	}

	/**
	 * @return the singleton
	 */
	public static OnDemandService getSingleton() {
		return singleton;
	}

	/**
	 * A worker that executes and responds to {@link OnDemandRequest}s.
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	protected class Worker implements Runnable, Comparable<Worker> {

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
		 *            The {@link OnDemandRequest} that this worker will respond
		 *            to.
		 */
		public Worker(Channel channel, OnDemandRequest request) {
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
		public int compareTo(Worker o) {
			return request.compareTo(o.request);
		}

	}

}
