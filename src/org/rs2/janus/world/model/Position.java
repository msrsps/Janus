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
	 * 
	 */
	private final int tileX;

	/**
	 * 
	 */
	private final int tileY;

	/**
	 * 
	 */
	private final int height;

	/**
	 * @param tileX
	 * @param tileY
	 * @param height
	 */
	public Position(int tileX, int tileY, int height) {
		super();
		this.tileX = tileX;
		this.tileY = tileY;
		this.height = height;
	}

	/**
	 * @return the tileX
	 */
	public int getTileX() {
		return tileX;
	}

	/**
	 * @return the tileY
	 */
	public int getTileY() {
		return tileY;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + tileX;
		result = prime * result + tileY;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (height != other.height)
			return false;
		if (tileX != other.tileX)
			return false;
		if (tileY != other.tileY)
			return false;
		return true;
	}

}
