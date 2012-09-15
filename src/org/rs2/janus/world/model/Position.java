/**
 * 
 */
package org.rs2.janus.world.model;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Position {

	/**
	 * The position on the x-axis.
	 */
	private final int x;

	/**
	 * The position on the y-axis.
	 */
	private final int y;

	/**
	 * The height of the position.
	 */
	private final int height;

	/**
	 * New instance.
	 * 
	 * @param x
	 *            The position on the x-axis.
	 * @param y
	 *            The position on the y-axis.
	 * @param height
	 *            The height of the position.
	 */
	public Position(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}

	/**
	 * 
	 * @param formatted
	 * @return
	 */
	public int getChunkX(boolean formatted) {
		return formatted ? (x >> 3) - 6 : (x >> 3);
	}

	/**
	 * 
	 * @param formatted
	 * @return
	 */
	public int getChunkY(boolean formatted) {
		return formatted ? (y >> 3) - 6 : (y >> 3);
	}

	/**
	 * 
	 * @return
	 */
	public int getRegionX() {
		return x >> 6;
	}

	/**
	 * 
	 * @return
	 */
	public int getRegionY() {
		return y >> 6;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

}
