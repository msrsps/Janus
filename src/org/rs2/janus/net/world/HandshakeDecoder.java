/**
 * 
 */
package org.rs2.janus.net.world;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.rs2.janus.net.ondemand.OnDemandRequestDecoder;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class HandshakeDecoder extends FrameDecoder {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(HandshakeDecoder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty
	 * .channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readable()) {
			int serviceCode = buffer.readByte();
			switch (serviceCode) {
			case 15:
				channel.getPipeline().addBefore(WorldChannelHandler.class.getSimpleName(), OnDemandRequestDecoder.class.getSimpleName(),
						new OnDemandRequestDecoder());
				break;
			default:
				log.warn("Service code " + serviceCode + " is unhandled.");
				return null;
			}

			ChannelBuffer handshake = ChannelBuffers.buffer(8);
			handshake.writeZero(8);
			channel.write(handshake);
			channel.getPipeline().remove(this);
		}
		return null;
	}

}
