/**
 * 
 */
package org.rs2.janus.net.archive;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * The handler for the archive server, it handles both Http and Jaggrab
 * requests.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveChannelHandler extends IdleStateAwareChannelUpstreamHandler {

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
		if (message instanceof ArchiveRequest) {
			ByteBuffer response = ((ArchiveRequest) message).getResponse();
			if (response == null) {
				e.getChannel().close();
			} else {
				e.getChannel().write(ChannelBuffers.wrappedBuffer(response)).addListener(ChannelFutureListener.CLOSE);
			}
		}
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
		e.getChannel().close();
	}
}
