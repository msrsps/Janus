/**
 * 
 */
package org.rs2.janus.archive.net;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * The {@link ChannelPipelineFactory} responsible for constructing pipelines
 * that will decode requests via the Jaggrab protocol.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class JaggrabPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The delimiter for the frame decoder.
	 */
	private static final ChannelBuffer delimiter = ChannelBuffers.buffer(2);

	/**
	 * The {@link ChannelHandler} that will handle all requests.
	 */
	private final ArchiveChannelHandler handler;

	/**
	 * The idle timer.
	 */
	private final Timer timer = new HashedWheelTimer();

	static {
		delimiter.writeByte(10);
		delimiter.writeByte(10);
	}

	/**
	 * New instance.
	 * 
	 * @param handler
	 *            The {@link ChannelHandler} that will handle all requests.
	 */
	public JaggrabPipelineFactory(ArchiveChannelHandler handler) {
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
		pipeline.addFirst(DelimiterBasedFrameDecoder.class.getSimpleName(), new DelimiterBasedFrameDecoder(8192, delimiter));
		pipeline.addLast(StringDecoder.class.getSimpleName(), new StringDecoder(Charset.forName("US-ASCII")));
		pipeline.addLast(StringToJaggrabDecoder.class.getSimpleName(), new StringToJaggrabDecoder());

		// No encoders as the Jaggrab Protocol simply accepts raw data as a
		// response.

		// Handlers
		pipeline.addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(timer, 15, 15, 0));
		pipeline.addLast("handler", handler);
		return pipeline;
	}
}
