/**
 * 
 */
package org.rs2.janus.net.world;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.rs2.janus.net.ondemand.OnDemandRequest;
import org.rs2.janus.net.ondemand.OnDemandWorker;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldChannelHandler extends IdleStateAwareChannelUpstreamHandler {

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
		if (message instanceof OnDemandRequest)
			new OnDemandWorker(e.getChannel(), (OnDemandRequest) message).run();
		super.messageReceived(ctx, e);
	}

}
