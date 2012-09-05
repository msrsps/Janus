/**
 * 
 */
package org.rs2.janus.util;

import net.burtleburtle.bob.rand.IsaacRandom;

/**
 * Constructs and wraps both of the {@link IsaacRandom}s responsible for
 * encoding and decoding.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class IsaacRandomPair {

	/**
	 * The {@link IsaacRandom} responsible for encoding.
	 */
	private final IsaacRandom encodingRandom;

	/**
	 * The {@link IsaacRandom} responsible for decoding.
	 */
	private final IsaacRandom decodingRandom;

	/**
	 * New instance.
	 * 
	 * @param clientSeed
	 *            The client seed.
	 * @param serverSeed
	 *            The server seed.
	 */
	public IsaacRandomPair(long clientSeed, long serverSeed) {
		int[] seed = new int[4];
		seed[0] = (int) (clientSeed >> 32);
		seed[1] = (int) clientSeed;
		seed[2] = (int) (serverSeed >> 32);
		seed[3] = (int) serverSeed;

		decodingRandom = new IsaacRandom(seed);

		for (int index = 0; index < seed.length; index++)
			seed[index] += 50;

		encodingRandom = new IsaacRandom(seed);
	}

	/**
	 * Decodes a value.
	 * 
	 * @param value
	 *            The encoded value.
	 * @return The decoded value.
	 */
	public int decode(int value) {
		return (value - decodingRandom.nextInt()) & 0xFF;
	}

	/**
	 * Encodes a value.
	 * 
	 * @param value
	 *            The decoded value.
	 * @return The encode value.
	 */
	public int encode(int value) {
		return (value + encodingRandom.nextInt()) & 0xFF;
	}

}
