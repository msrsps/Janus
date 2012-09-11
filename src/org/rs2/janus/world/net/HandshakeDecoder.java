/**
 * 
 */
package org.rs2.janus.world.net;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.rs2.janus.world.net.login.LoginRequestDecoder;
import org.rs2.janus.world.net.login.LoginResponseEncoder;
import org.rs2.janus.world.net.ondemand.OnDemandRequestDecoder;

/**
 * Reads the initial service code by the client and adds the correct decoders.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class HandshakeDecoder extends FrameDecoder {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(HandshakeDecoder.class);

	/**
	 * New instance
	 */
	public HandshakeDecoder() {
		super(true);
	}

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
			case 14: // Login service
				ctx.getPipeline().addFirst(LoginResponseEncoder.class.getSimpleName(), new LoginResponseEncoder());
				ctx.getPipeline().addBefore("handler", LoginRequestDecoder.class.getSimpleName(), new LoginRequestDecoder());
				completeHandshake(ctx.getChannel());
				break;
			case 15: // On-Demand service
				ctx.getPipeline().addBefore("handler", OnDemandRequestDecoder.class.getSimpleName(), new OnDemandRequestDecoder());
				completeHandshake(ctx.getChannel());
				break;
			default:
				log.warn("Service code " + serviceCode + " is unhandled.");
				return null;
			}

			ctx.getPipeline().remove(this);
			return buffer;

		}
		return null;
	}

	/**
	 * Completes the handshake by sending 8 bytes with the value of 0.
	 * 
	 * @param channel
	 *            The channel to send to.
	 */
	private void completeHandshake(Channel channel) {
		ChannelBuffer handshake = ChannelBuffers.buffer(17);
		handshake.writeZero(8);
		channel.write(handshake);
	}

}