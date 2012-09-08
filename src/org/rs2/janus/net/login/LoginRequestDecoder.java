/**
 * 
 */
package org.rs2.janus.net.login;

import java.math.BigInteger;
import java.util.Random;

import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.util.IsaacRandomPair;
import org.rs2.janus.util.TextUtil;
import org.rs2.janus.world.model.entity.character.player.PlayerCredientals;

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
	 * 
	 */
	private static final BigInteger RSA_MODULUS = new BigInteger(JanusProperties.getString("RSA_MODULUS"));

	/**
	 * 
	 */
	private static final BigInteger RSA_EXPONENT = new BigInteger(JanusProperties.getString("RSA_EXPONENT"));

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
	private Object decodePayload(Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() >= payloadSize) {
			int uid;
			int lowMemoryFlag;
			int securePayloadSize;
			int[] archiveCrcs = new int[9];
			long clientSeed;
			String user;
			String pass;
			IsaacRandomPair isaacPair;

			ChannelBuffer payload = ChannelBuffers.buffer(payloadSize);
			buffer.readBytes(payload);

			if (payload.readUnsignedByte() != 0xFF)
				throw new Exception("Magic id mismatch.");

			if (payload.readUnsignedShort() != JanusProperties.getInt("SERVER_REVISION"))
				throw new Exception("Wrong revision.");

			lowMemoryFlag = payload.readUnsignedByte();

			for (int index = 0; index < 9; index++)
				archiveCrcs[index] = payload.readInt();

			securePayloadSize = payload.readUnsignedByte();

			if (securePayloadSize != payloadSize - 41)
				throw new Exception("Secure payload size mismatch");

			ChannelBuffer securePayload = ChannelBuffers.buffer(securePayloadSize);
			payload.readBytes(securePayload);

			BigInteger bigInteger = new BigInteger(securePayload.array()).modPow(RSA_EXPONENT, RSA_MODULUS);
			securePayload = ChannelBuffers.wrappedBuffer(bigInteger.toByteArray());

			if (securePayload.readUnsignedByte() != 10)
				throw new Exception("Secure id mismatch.");

			clientSeed = securePayload.readLong();

			if (securePayload.readLong() != serverSeed)
				return new Exception("Server seed mismatch.");

			isaacPair = new IsaacRandomPair(clientSeed, clientSeed);

			uid = securePayload.readInt();

			user = TextUtil.decodeText(securePayload);
			pass = TextUtil.decodeText(securePayload);

			return new LoginRequest(new PlayerCredientals(user, pass, uid, isaacPair), lowMemoryFlag == 1, loginType == 18, namehash, archiveCrcs);

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
