/**
 * 
 */
package org.rs2.janus.net.packet;

import net.burtleburtle.bob.rand.IsaacRandom;

import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.net.packet.PacketConstants.PacketHeader;
import org.rs2.janus.world.model.entity.character.player.Client;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketDecoder extends StatefulFrameDecoder<PacketDecoder.State> {

	/**
	 * Opcode of the packet that is current decoding.
	 */
	private int opcode;

	/**
	 * Header of the packet that is current decoding.
	 */
	private PacketHeader header;

	/**
	 * Size of the packet that is current decoding.
	 */
	private int packetSize;

	/**
	 * @param state
	 */
	public PacketDecoder() {
		super(State.OPCODE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.util.StatefulFrameDecoder#decode(org.jboss.netty.channel.
	 * ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * org.jboss.netty.buffer.ChannelBuffer, java.lang.Enum)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state) throws Exception {
		switch (state) {
		case OPCODE:
			return decodeOpcode(channel, buffer);
		case HEADER:
			return decodeHeader(ctx, buffer);
		case PAYLOAD:
			return decodePayload(ctx, buffer);
		}
		return null;
	}

	/**
	 * @param ctx
	 * @param buffer
	 * @return
	 */
	private Object decodeOpcode(Channel channel, ChannelBuffer buffer) {
		if (buffer.readable()) {
			IsaacRandom decoder = ((Client) channel.getAttachment()).getDecoder();

			opcode = (buffer.readUnsignedByte() - decoder.nextInt()) & 0xFF;

			header = PacketHeader.valueOf(PacketConstants.PACKET_SIZE[opcode]);

			if (header == PacketHeader.FIXED) {
				packetSize = PacketConstants.PACKET_SIZE[opcode];
				if (packetSize == 0)
					return new Packet(opcode, PacketHeader.FIXED, ChannelBuffers.EMPTY_BUFFER);
				else
					setState(State.PAYLOAD);
			} else {
				setState(State.HEADER);
			}
		}
		return null;
	}

	/**
	 * @param ctx
	 * @param buffer
	 * @return
	 */
	private Object decodeHeader(ChannelHandlerContext ctx, ChannelBuffer buffer) {
		System.out.println(header);
		if (header == PacketHeader.VARIABLE_BYTE) {
			if (buffer.readable()) {
				packetSize = buffer.readUnsignedByte();
				setState(State.PAYLOAD);
			}
		} else if (header == PacketHeader.VARIABLE_SHORT) {
			if (buffer.readableBytes() >= 2) {
				packetSize = buffer.readUnsignedShort();
				setState(State.PAYLOAD);
			}
		}
		return null;
	}

	/**
	 * @param ctx
	 * @param buffer
	 * @return
	 */
	private Object decodePayload(ChannelHandlerContext ctx, ChannelBuffer buffer) {
		if (buffer.readableBytes() >= packetSize) {
			ChannelBuffer payload = ChannelBuffers.buffer(packetSize);
			buffer.readBytes(payload);
			setState(State.OPCODE);
			return new Packet(opcode, header, payload);
		}
		return null;
	}

	protected enum State {
		OPCODE, HEADER, PAYLOAD;
	}

}
