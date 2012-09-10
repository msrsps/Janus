/**
 * 
 */
package org.rs2.janus.net.login;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.rs2.janus.net.login.LoginResponse.LoginResponseCode;
import org.rs2.janus.net.packet.PacketDecoder;
import org.rs2.janus.net.packet.PacketEncoder;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginResponseEncoder extends OneToOneEncoder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss
	 * .netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * java.lang.Object)
	 */
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (msg instanceof LoginResponse) {
			LoginResponse response = (LoginResponse) msg;
			ChannelBuffer buffer;
			if (response.getResponseCode() == LoginResponseCode.SUCCESSFUL) {
				ctx.getPipeline().remove(this);
				ctx.getPipeline().remove(LoginRequestDecoder.class.getSimpleName());

				channel.getPipeline().addFirst(PacketEncoder.class.getSimpleName(), new PacketEncoder());
				channel.getPipeline().addBefore("handler", PacketDecoder.class.getSimpleName(), new PacketDecoder());

				buffer = ChannelBuffers.buffer(3);
				buffer.writeByte(response.getResponseCode().getCode());
				buffer.writeByte(response.getPrivilegeLevel());
				buffer.writeByte(response.getFlagged());

			} else {
				buffer = ChannelBuffers.buffer(1);
				buffer.writeByte(response.getResponseCode().getCode());
			}

			return buffer;
		}
		return msg;
	}

}
