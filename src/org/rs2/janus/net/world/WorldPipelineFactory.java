/**
 * 
 */
package org.rs2.janus.net.world;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.rs2.janus.net.service.Service;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldPipelineFactory implements ChannelPipelineFactory {

	/**
	 * 
	 */
	private final HashedWheelTimer timer = new HashedWheelTimer();

	/**
	 * 
	 */
	private final WorldChannelHandler handler;

	/**
	 * 
	 */
	private final Service loginService;

	/**
	 * 
	 */
	public WorldPipelineFactory(Service loginService, WorldChannelHandler handler) {
		this.handler = handler;
		this.loginService = loginService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addFirst(HandshakeDecoder.class.getSimpleName(), new HandshakeDecoder(loginService));

		pipeline.addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(timer, 15, 15, 0));
		pipeline.addLast("handler", handler);
		return pipeline;
	}

}
