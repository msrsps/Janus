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
public class PacketReader {

	/**
	 * The buffer the is wrapped by this reader.
	 */
	private final ChannelBuffer internalBuffer;

	/**
	 * If the reader is in bit access.
	 */
	private boolean bitAccess = false;

	/**
	 * The bit position of the reader.
	 */
	private int bitPosition;

	/**
	 * New instance
	 * 
	 * @param packet
	 *            The packet who's payload will be read.
	 */
	public PacketReader(Packet packet) {
		this.internalBuffer = packet.getPayload();
	}

	/**
	 * Reads a unsigned piece of data from the packet.
	 * 
	 * @param type
	 *            The type of data.
	 * @param endianness
	 *            The endianness of the data.
	 * @param transformation
	 *            The transformation to apply to the Data.
	 * @return The read data.
	 */
	public Number readUnsigned(DataType type, DataEndianness endianness, DataTransformation transformation) {
		if (type == DataType.LONG)
			new IllegalArgumentException("Longs cannot be read as unsigned.");

		long value = 0;
		switch (endianness) {
		case LITTLE:
			for (int index = 0; index < type.getLength(); index++)
				value |= ((internalBuffer.readByte() & 0xFF) << (index * 8));
			break;
		case BIG:
			for (int index = type.getLength() - 1; index >= 0; index--)
				value |= ((internalBuffer.readByte() & 0xFF) << (index * 8));
			break;
		case MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints.");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians.");

			value |= ((internalBuffer.readByte() & 0xFF) << 8);
			value |= ((internalBuffer.readByte() & 0xFF) << 0);
			value |= ((internalBuffer.readByte() & 0xFF) << 24);
			value |= ((internalBuffer.readByte() & 0xFF) << 16);
			break;
		case INVERTED_MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints.");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians.");

			value |= ((internalBuffer.readByte() & 0xFF) << 16);
			value |= ((internalBuffer.readByte() & 0xFF) << 24);
			value |= ((internalBuffer.readByte() & 0xFF) << 0);
			value |= ((internalBuffer.readByte() & 0xFF) << 8);
			break;
		default:
			throw new IllegalArgumentException("Unknown endianness.");
		}

		value = transformation.applyTo(value);

		return value;
	}

	/**
	 * Reads a signed piece of data from the packet.
	 * 
	 * @param type
	 *            The type of data.
	 * @param endianness
	 *            The endianness of the data.
	 * @param transformation
	 *            The transformation to apply to the Data.
	 * @return The read data.
	 */
	public Number read(DataType type, DataEndianness endianness, DataTransformation transformation) {
		long value = 0;
		switch (endianness) {
		case LITTLE:
			for (int index = 0; index < type.getLength(); index++)
				value |= (internalBuffer.readByte() << (index * 8));
			break;
		case BIG:
			for (int index = type.getLength() - 1; index >= 0; index--)
				value |= (internalBuffer.readByte() << (index * 8));
			break;
		case MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints.");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians.");

			value |= (internalBuffer.readByte() << 8);
			value |= (internalBuffer.readByte() << 0);
			value |= (internalBuffer.readByte() << 24);
			value |= (internalBuffer.readByte() << 16);
			break;
		case INVERTED_MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints.");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians.");

			value |= (internalBuffer.readByte() << 16);
			value |= (internalBuffer.readByte() << 24);
			value |= (internalBuffer.readByte() << 0);
			value |= (internalBuffer.readByte() << 8);
			break;
		default:
			throw new IllegalArgumentException("Unknown endianness.");
		}

		value = transformation.applyTo(value);

		return value;
	}

	/**
	 * Reads bits from buffer.
	 * 
	 * @param bits
	 *            Number of bits to reader.
	 * @return The value of the combined bits.
	 */
	public int readBits(int bits) {
		int bytePosition = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 0x7);
		int value = 0;
		bitPosition += bits;

		for (/**/; bits > bitOffset; bitOffset = 8) {
			value += (internalBuffer.getByte(bytePosition++) & PacketConstants.BIT_MASK[bitOffset]) << bits - bitOffset;
			bits -= bitOffset;
		}
		if (bits == bitOffset) {
			value += internalBuffer.getByte(bytePosition) & PacketConstants.BIT_MASK[bitOffset];
		} else {
			value += internalBuffer.getByte(bytePosition) >> bitOffset - bits & PacketConstants.BIT_MASK[bits];
		}
		return value;
	}

	/**
	 * Puts the reader into bit access and sets the bit fields.
	 */
	public void setBitAccess() {
		if (bitAccess == true)
			throw new IllegalStateException("Already in bit access.");

		bitAccess = true;
		bitPosition = internalBuffer.readerIndex() << 3;
	}

	/**
	 * Puts the reader into byte access and resets the bit fields.
	 */
	public void setByteAccess() {
		if (bitAccess == false)
			throw new IllegalStateException("Already in byte access.");

		bitAccess = false;
		internalBuffer.readerIndex((bitPosition + 7) / 8);
	}

}
