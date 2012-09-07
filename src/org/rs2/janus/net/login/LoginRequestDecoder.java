/**
 * 
 */
package org.rs2.janus.net.login;

import java.util.Random;

import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginRequestDecoder extends StatefulFrameDecoder<LoginRequestDecoder.State> {

	/**
	 * The random that will generate server seeds.
	 */
	private static final Random serverSeedGen = new Random(System.currentTimeMillis());

	/**
	 * The generated server seed.
	 */
	private final long serverSeed = serverSeedGen.nextLong();

	/**
	 * The name hash based on player's name.
	 */
	private int namehash;

	/**
	 * The type of connection, normal or reconnection.
	 */
	private int loginType;

	/**
	 * The size of the payload in bytes.
	 */
	private int payloadSize;

	/**
	 * @param state
	 */
	public LoginRequestDecoder() {
		super(State.HANDSHAKE);
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
		case HANDSHAKE:
			decodeHandshake(channel, buffer);
			return null;
		case HEADER:
			decodeHeader(channel, buffer);
			return null;
		case PAYLOAD:
			return decodePayload(channel, buffer);
		}
		return null;
	}

	/**
	 * @param channel
	 * @param buffer
	 */
	private void decodeHandshake(Channel channel, ChannelBuffer buffer) {
		if (buffer.readable()) {
			int namehash = buffer.readUnsignedByte();

			ChannelBuffer msg = ChannelBuffers.buffer(9);
			msg.writeZero(1);
			msg.writeLong(serverSeed);
			channel.write(msg);

			setState(State.HEADER);
		}
	}

	/**
	 * @param channel
	 * @param buffer
	 */
	private void decodeHeader(Channel channel, ChannelBuffer buffer) {
		if (buffer.readableBytes() >= 2) {
			loginType = buffer.readUnsignedByte();

			payloadSize = buffer.readUnsignedByte();

			setState(State.PAYLOAD);
		}
	}

	/**
	 * @param channel
	 * @param buffer
	 * @return
	 */
	private Object decodePayload(Channel channel, ChannelBuffer buffer) {
		if (buffer.readableBytes() >= payloadSize) {
			ChannelBuffer payload = ChannelBuffers.buffer(payloadSize);
			buffer.readBytes(payload);

			ChannelBuffer loginResponse = ChannelBuffers.buffer(3);
			loginResponse.writeByte(2);
			loginResponse.writeByte(0);
			loginResponse.writeByte(0);

			channel.write(loginResponse);

			channel.getPipeline().remove(this);
		}
		return null;
	}

	/**
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	protected enum State {
		HANDSHAKE, HEADER, PAYLOAD;
	}
}
