/**
 * 
 */
package org.rs2.janus.world.net;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.rs2.janus.world.WorldContext;
import org.rs2.janus.world.model.entity.character.player.Client;
import org.rs2.janus.world.net.packet.Packet;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldChannelHandler extends IdleStateAwareChannelUpstreamHandler {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(WorldChannelHandler.class);

	/**
	 * 
	 */
	private final WorldContext context;

	/**
	 * 
	 */
	public WorldChannelHandler(WorldContext context) {
		this.context = context;
	}

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

		if (message instanceof Packet) {
			((Client) ctx.getChannel().getAttachment()).receive((Packet) message);
		} else if (message instanceof Request) {
			context.getService(message.getClass()).messageReceived(context, ctx.getChannel(), (Request) message);
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
		e.getCause().printStackTrace();
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
