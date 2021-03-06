/**
 * 
 */
package org.rs2.janus.util;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class TextUtil {

	/**
	 * Reads a line from a channel buffer.
	 */
	public static String readLine(ChannelBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		short character;
		while (buffer.readable() && (character = buffer.readUnsignedByte()) != 10)
			builder.append((char) character);
		return builder.toString();
	}

	/**
	 * Private constructor.
	 */
	private TextUtil() {

	}

}
