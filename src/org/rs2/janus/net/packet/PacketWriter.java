/**
 * 
 */
package org.rs2.janus.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.rs2.janus.net.packet.PacketConstants.DataEndianness;
import org.rs2.janus.net.packet.PacketConstants.DataTransformation;
import org.rs2.janus.net.packet.PacketConstants.DataType;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketWriter {

	/**
	 * The buffer the is wrapped by this writer.
	 */
	private final ChannelBuffer internalBuffer;

	/**
	 * If the writer is in bit access.
	 */
	private boolean bitAccess = false;

	/**
	 * The bit position of the writer.
	 */
	private int bitPosition;

	/**
	 * New instance
	 * 
	 * @param packet
	 *            The packet who's payload will be written.
	 */
	public PacketWriter(Packet packet) {
		this.internalBuffer = packet.getPayload();
	}

	/**
	 * Writes data into the buffer.
	 * 
	 * @param value
	 *            The value of the data.
	 * @param type
	 *            The type of Data.
	 * @param endianness
	 *            The endianness of the data.
	 * @param transformation
	 *            The transformation to apply to the data.
	 */
	public void write(long value, DataType type, DataEndianness endianness, DataTransformation transformation) {
		value = transformation.applyTo(value);
		switch (endianness) {
		case BIG:
			for (int index = type.getLength(); index > 0; index--)
				internalBuffer.writeByte((byte) value >> (index * 8));
			break;
		case LITTLE:
			for (int index = 0; index < type.getLength(); index++)
				internalBuffer.writeByte((byte) value >> (index * 8));
			break;
		case MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints!");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians!");

			internalBuffer.writeByte((byte) (value >> 8));
			internalBuffer.writeByte((byte) value);
			internalBuffer.writeByte((byte) (value >> 24));
			internalBuffer.writeByte((byte) (value >> 16));
			break;
		case INVERTED_MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints!");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians!");

			internalBuffer.writeByte((byte) (value >> 16));
			internalBuffer.writeByte((byte) (value >> 24));
			internalBuffer.writeByte((byte) value);
			internalBuffer.writeByte((byte) (value >> 8));
			break;
		}
	}

	/**
	 * Writes bits to the buffer.
	 * 
	 * @param bits
	 *            Number of bits to write.
	 * @param value
	 *            The value of the combined bits.
	 */
	public void writeBits(int bits, int value) {
		int bytePosition = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 7);
		bitPosition += bits;

		for (; bits > bitOffset; bitOffset = 8) {
			int tmp = internalBuffer.getByte(bytePosition);
			tmp &= ~PacketConstants.BIT_MASK[bitOffset];
			tmp |= (value >> (bits - bitOffset)) & PacketConstants.BIT_MASK[bitOffset];
			internalBuffer.setByte(bytePosition++, tmp);
			bits -= bitOffset;
		}
		if (bits == bitOffset) {
			int tmp = internalBuffer.getByte(bytePosition);
			tmp &= ~PacketConstants.BIT_MASK[bitOffset];
			tmp |= value & PacketConstants.BIT_MASK[bitOffset];
			internalBuffer.setByte(bytePosition, tmp);
		} else {
			int tmp = internalBuffer.getByte(bytePosition);
			tmp &= ~(PacketConstants.BIT_MASK[bits] << (bitOffset - bits));
			tmp |= (value & PacketConstants.BIT_MASK[bits]) << (bitOffset - bits);
			internalBuffer.setByte(bytePosition, tmp);
		}
	}

	/**
	 * Puts the writer into bit access and sets the bit fields.
	 */
	public void setBitAccess() {
		if (bitAccess == true)
			throw new IllegalStateException("Already in bit access.");

		bitAccess = true;
		bitPosition = internalBuffer.writerIndex() << 3;
	}

	/**
	 * Puts the writer into byte access and resets the bit fields.
	 */
	public void setByteAccess() {
		if (bitAccess == false)
			throw new IllegalStateException("Already in byte access.");

		bitAccess = false;
		internalBuffer.writerIndex((bitPosition + 7) / 8);
	}

}
