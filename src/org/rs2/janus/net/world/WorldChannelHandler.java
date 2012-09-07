/**
 * 
 */
package org.rs2.janus.net.world;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.rs2.janus.net.request.Request;
import org.rs2.janus.net.service.Service;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldChannelHandler extends IdleStateAwareChannelUpstreamHandler {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(WorldChannelHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(
	 * org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Object message = e.getMessage();
		if (ctx.getChannel().getAttachment() != null) {
			Object attachment = e.getChannel().getAttachment();
			if (attachment instanceof Service) {
				((Service<Request>) attachment).serviceRequest(ctx, (Request) message);
			} else if (attachment instanceof Object) {
				// Handle packets to player.
			}
		} else {
			log.info("Unhandled message [message=" + message + ", channel=" + ctx.getChannel() + "]");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(
	 * org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		log.error("Exception Caught [cause=" + e.getCause() + ", channel=" + ctx.getChannel() + "], closing channel.");
		ctx.getChannel().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler#
	 * channelIdle(org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.handler.timeout.IdleStateEvent)
	 */
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
		log.info("Channel Idle [channel=" + ctx.getChannel() + "], closing channel.");
		ctx.getChannel().close();
	}

}
