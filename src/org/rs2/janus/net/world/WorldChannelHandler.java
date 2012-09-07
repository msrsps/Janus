/**
 * 
 */
package org.rs2.janus.net.world;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.net.ondemand.OnDemandRequest;
import org.rs2.janus.net.ondemand.OnDemandWorker;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldChannelHandler extends IdleStateAwareChannelUpstreamHandler {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(WorldChannelHandler.class);

	/**
	 * Thread pool for executing on-demand requests.
	 */
	private final ExecutorService ondemandExecutor = new ThreadPoolExecutor(JanusProperties.getInt("ONDEMAND_WORKER_THREADS"), JanusProperties
			.getInt("ONDEMAND_WORKER_THREADS"), 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());

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
			ondemandExecutor.execute(new OnDemandWorker(e.getChannel(), (OnDemandRequest) message));
		else
			log.info("Unhandled message [message=" + message + ", channel=" + ctx.getChannel() + "]");
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
