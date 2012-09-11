/**
 * 
 */
package org.rs2.janus.archive.net;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * The {@link ChannelPipelineFactory} responsible for constructing pipelines
 * that will decode requests via the Http protocol.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class HttpPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The {@link ChannelHandler} that will handle all requests.
	 */
	private final ArchiveChannelHandler handler;

	/**
	 * The idle timer.
	 */
	private final Timer timer = new HashedWheelTimer();

	/**
	 * New instance.
	 * 
	 * @param handler
	 *            The {@link ChannelHandler} that will handle all requests.
	 */
	public HttpPipelineFactory(ArchiveChannelHandler handler) {
		this.handler = handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		// Decoders
		pipeline.addFirst(HttpRequestDecoder.class.getSimpleName(), new HttpRequestDecoder());
		pipeline.addLast(HttpChunkAggregator.class.getSimpleName(), new HttpChunkAggregator(8192));
		pipeline.addLast(HttpToArchiveDecoder.class.getSimpleName(), new HttpToArchiveDecoder());

		// Encoders
		pipeline.addLast(HttpResponseEncoder.class.getSimpleName(), new HttpResponseEncoder());
		pipeline.addLast(ArchiveToHttpEncoder.class.getSimpleName(), new ArchiveToHttpEncoder());

		// Handlers
		pipeline.addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(timer, 15, 15, 0));
		pipeline.addLast(ArchiveChannelHandler.class.getSimpleName(), handler);
		return pipeline;
	}

}
