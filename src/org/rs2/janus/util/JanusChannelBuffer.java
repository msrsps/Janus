/**
 * 
 */
package org.rs2.janus.util;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class JanusChannelBuffer {

	/**
	 * List of bit masks used to convert data from signed to unsigned.
	 */
	private static final int bitmasks[] = { 0x00, 0x01, 0x03, 0x07, 0x0f, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff,
			0xffff, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
			0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff };

	/**
	 * The {@link ChannelBuffer} that is being wrapped
	 */
	private final ChannelBuffer buffer;

	/**
	 * The current bit position of the writer.
	 */
	private int writerBitIndex;

	/**
	 * A buffer containing the writer's bits.
	 */
	private int writerBitBuffer;

	/**
	 * The current bit position of the reader.
	 */
	private int readerBitIndex;

	/**
	 * A buffer containing the reader's bits.
	 */
	private int readerBitBuffer;

	/**
	 * If the wrapper is in bit access.
	 */
	private boolean bitAccess = false;

	/**
	 * New instance.
	 * 
	 * @param buffer
	 *            The buffer that will be wrapped.
	 */
	public JanusChannelBuffer(ChannelBuffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * Transfers the specified source buffer's data to this buffer starting at
	 * the current {@code writerIndex} until the source buffer's position
	 * reaches its limit, and increases the {@code writerIndex} by the number of
	 * the transferred bytes.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code src.remaining()} is greater than
	 *             {@code this.writableBytes}
	 */
	public void writeBytes(ByteBuffer src) {
		checkByteAccess();
		buffer.writeBytes(src);
	}

	/**
	 * Transfers the specified source buffer's data to this buffer starting at
	 * the current {@code writerIndex} until the source buffer's position
	 * reaches its limit, and increases the {@code writerIndex} by the number of
	 * the transferred bytes.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code src.remaining()} is greater than
	 *             {@code this.writableBytes}
	 */
	public void writeBytes(ChannelBuffer src) {
		checkByteAccess();
		buffer.writeBytes(src);
	}

	/**
	 * Transfers this buffer's data to the specified destination starting at the
	 * current {@code readerIndex} until the destination's position reaches its
	 * limit, and increases the {@code readerIndex} by the number of the
	 * transferred bytes.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code dst.remaining()} is greater than
	 *             {@code this.readableBytes}
	 */
	public void readBytes(ByteBuffer dst) {
		checkByteAccess();
		buffer.readBytes(dst);
	}

	/**
	 * Transfers this buffer's data to the specified destination starting at the
	 * current {@code readerIndex} until the destination's position reaches its
	 * limit, and increases the {@code readerIndex} by the number of the
	 * transferred bytes.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code dst.remaining()} is greater than
	 *             {@code this.readableBytes}
	 */
	public void readBytes(ChannelBuffer dst) {
		checkByteAccess();
		buffer.readBytes(dst);
	}

	/**
	 * Reads unsigned data from the buffer.
	 * 
	 * @param type
	 *            The type of data to read.
	 * @param endianness
	 *            The endianness of the data.
	 * @param transformation
	 *            The transformation to apply to the data.
	 * @return The data.
	 */
	public long readUnsigned(DataType type, DataEndianness endianness, DataTransformation transformation) {
		checkByteAccess();
		return signedToUnsigned(type, readSigned(type, endianness, transformation));
	}

	/**
	 * Reads signed data from the buffer.
	 * 
	 * @param type
	 *            The type of data to read.
	 * @param endianness
	 *            The endianness of the data.
	 * @param transformation
	 *            The transformation to apply to the data.
	 * @return The data.
	 */
	public long readSigned(DataType type, DataEndianness endianness, DataTransformation transformation) {
		checkByteAccess();

		long value = 0;

		switch (endianness) {
		case LITTLE:
			for (int index = 0; index < type.getLength(); index++)
				value |= ((buffer.readByte() & 0xFF) << (index * 8));
			break;
		case BIG:
			for (int index = type.getLength() - 1; index >= 0; index--)
				value |= ((buffer.readByte() & 0xFF) << (index * 8));
			break;
		case MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints!");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians!");

			value |= ((buffer.readByte() & 0xFF) << 8);
			value |= ((buffer.readByte() & 0xFF) << 0);
			value |= ((buffer.readByte() & 0xFF) << 24);
			value |= ((buffer.readByte() & 0xFF) << 16);
			break;
		case INVERTED_MIDDLE:
			if (type != DataType.INT)
				throw new IllegalArgumentException("You can only use middle endians with ints!");

			if (transformation != DataTransformation.NONE)
				throw new IllegalArgumentException("You cannot transform middle endians!");

			value |= ((buffer.readByte() & 0xFF) << 16);
			value |= ((buffer.readByte() & 0xFF) << 24);
			value |= ((buffer.readByte() & 0xFF) << 0);
			value |= ((buffer.readByte() & 0xFF) << 8);
			break;
		default:
			throw new IllegalArgumentException("Unknown endianness!");
		}

		value = transformation.transform(value);

		return value;
	}

	/**
	 * Writes x number of bits containing the given value.
	 * 
	 * @param bits
	 *            The number of bits to write.
	 * @param value
	 *            The value contained by the bits.
	 */
	public void writeBits(int bits, int value) {
		checkBitAccess();

		if (bits > 32)
			throw new IllegalArgumentException("You can only write 32 bits at a time!");

		for (int index = 0; index < bits; index++)
			writeBit((value >>> (bits - index - 1) & 1));
	}

	/**
	 * Writes a single bit.
	 * 
	 * @param value
	 *            The value to write.
	 */
	public void writeBit(int value) {
		checkBitAccess();

		writerBitBuffer <<= 1;

		if (value == 1)
			writerBitBuffer |= 1;

		writerBitIndex++;
		checkBitBuffer();
	}

	/**
	 * Reads x number of bits.
	 * 
	 * @param bits
	 *            Number of bits to read.
	 * @return The value of the bits.
	 */
	public int readBits(int bits) {
		checkBitAccess();

		int tmp = 0;

		if (bits > 32)
			throw new IllegalArgumentException("You can only read 32 bits at a time!");

		for (int index = 0; index < bits; index++) {
			tmp <<= 1;

			if (readBit() == 1)
				tmp |= 1;
		}

		return tmp;
	}

	/**
	 * Reads a single bit.
	 * 
	 * @return The value of the bit.
	 */
	public int readBit() {
		checkBitAccess();

		int tmp = (readerBitBuffer >> --readerBitIndex) & 1;

		checkBitBuffer();
		return tmp;
	}

	/**
	 * Converts signed data to unsigned data.
	 * 
	 * @param type
	 *            The type of data to convert.
	 * @param value
	 *            The value of the data.
	 * @return The converted data.
	 */
	private static long signedToUnsigned(DataType type, long value) {
		return (value & bitmasks[(type.getLength() * 8) - 1]);
	}

	/**
	 * Checks if the buffer is in byte access.
	 * 
	 * @throws IllegalStateException
	 *             If the wrapper isn't using byte access.
	 */
	public void checkByteAccess() {
		if (bitAccess == true)
			throw new IllegalStateException("Buffer is not in byte access!");
	}

	/**
	 * Checks if the buffer is in bit access.
	 * 
	 * @throws IllegalStateException
	 *             If the wrapper isn't using bit access
	 */
	public void checkBitAccess() {
		if (bitAccess == false)
			throw new IllegalStateException("Buffer is not in bit access!");
	}

	/**
	 * Checks if the bit buffers need to be flushed/filled.
	 */
	public void checkBitBuffer() {
		if (writerBitIndex == 8 && buffer.writable())
			flushBitBuffer();

		if (readerBitIndex == 0 && buffer.readable())
			fillBitBuffer();
	}

	/**
	 * Flushes the bit writer.
	 */
	public void flushBitBuffer() {
		buffer.writeByte(writerBitBuffer);
		writerBitIndex = 0;
		writerBitBuffer = 0;
	}

	/**
	 * Fills the bit reader.
	 */
	public void fillBitBuffer() {
		readerBitBuffer = buffer.readByte();
		readerBitIndex = 8;
	}

	/**
	 * Sets the wrapper to byte access.
	 */
	public void setByteAccess() {
		bitAccess = false;
		readerBitIndex = 8;
		writerBitIndex = 0;
		readerBitBuffer = 0;
		writerBitBuffer = 0;
	}

	/**
	 * Sets the wrapper to bit access.
	 */
	public void setBitAccess() {
		bitAccess = true;
		readerBitIndex = 8;
		writerBitIndex = 0;
		readerBitBuffer = buffer.readByte();
		writerBitBuffer = 0;
	}

	/**
	 * Enum containing all of the different data types that can be read or
	 * written.
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	public enum DataType {
		BYTE(1), SHORT(2), INT(4), LONG(8), TRI_BYTE(3);

		/**
		 * The length of the data type in bytes.
		 */
		private final int length;

		/**
		 * New instance.
		 * 
		 * @param length
		 *            The length of the data type in bytes.
		 */
		private DataType(int length) {
			this.length = length;
		}

		/**
		 * Gets the length of the data type in bytes.
		 * 
		 * @return The length of the data type in bytes.
		 */
		public int getLength() {
			return length;
		}
	}

	/**
	 * Enum containing all of the different Endianness's that data can be
	 * encoded in.
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	public enum DataEndianness {
		BIG, LITTLE, MIDDLE, INVERTED_MIDDLE;
	}

	/**
	 * Enum containing all of the different transformations that can be applied
	 * to data.
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	public enum DataTransformation {
		NONE, ADD, SUBTRACT, NEGATE;

		/**
		 * Transforms a piece of data.
		 * 
		 * @param value
		 *            The value of the data.
		 * @return The transformed value of the data.
		 */
		public long transform(long value) {
			switch (this) {
			case ADD:
				return value + 128;
			case SUBTRACT:
				return value - 128;
			case NEGATE:
				return -value;
			case NONE:
				return value;
			default:
				throw new IllegalArgumentException("Unknown transformation.");
			}
		}
	}

}
