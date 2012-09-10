/**
 * 
 */
package org.rs2.janus.net.packet;

import net.burtleburtle.bob.rand.IsaacRandom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.rs2.janus.net.packet.PacketConstants.PacketHeader;
import org.rs2.janus.world.model.entity.character.player.Client;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketEncoder extends OneToOneEncoder {

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
		if (msg instanceof Packet) {
			Packet packet = (Packet) msg;
			ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
			IsaacRandom encoder = ((Client) channel.getAttachment()).getEncoder();

			buffer.writeByte((encoder.nextInt() + packet.getOpcode()) & 0xFF);

			if (packet.getHeader() == PacketHeader.VARIABLE_BYTE)
				buffer.writeByte(packet.getPayload().capacity());
			else if (packet.getHeader() == PacketHeader.VARIABLE_SHORT)
				buffer.writeShort(packet.getPayload().capacity());

			buffer.writeBytes(packet.getPayload());

			return buffer;

		}
		return msg;
	}

}
