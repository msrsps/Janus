/**
 * 
 */
package org.rs2.janus.net.packet;

/**
 * The packet constants.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketConstants {

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
		 * Applies a transformation a piece of data.
		 * 
		 * @param value
		 *            The value of the data.
		 * @return The transformed value of the data.
		 */
		public long applyTo(long value) {
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

	/**
	 * Represents a type of packet, they can be Fixed (No-header), Variable Byte
	 * ((byte) payload.capacity()), or Variable Short ((Short)
	 * payload.capacity()).
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	public enum PacketHeader {

		FIXED, VARIABLE_BYTE, VARIABLE_SHORT;

		public static PacketHeader valueOf(int value) {
			switch (value) {
			case -1:
				return VARIABLE_BYTE;
			case -2:
				return VARIABLE_SHORT;
			default:
				return FIXED;
			}
		}

	}

	/**
	 * The know packet sizes, 0 empty, -1 is Variable Byte, -2 is Variable
	 * Short, else is the actual size.
	 */
	public static final int[] PACKET_SIZE = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 0, 0, // 50
			8, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, // 250
	};

	/**
	 * List of bit masks for bits 0-31.
	 */
	public static final int BIT_MASK[] = { 0x00, 0x01, 0x03, 0x07, 0x0f, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff,
			0xffff, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
			0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff };

	/**
	 * Private constructor.
	 */
	private PacketConstants() {
	}

}
