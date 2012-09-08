/**
 * 
 */
package org.rs2.janus.world.model.entity;

import org.rs2.janus.world.model.Position;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Entity {

	/**
	 * 
	 */
	private Position position;

	/**
	 * @param position
	 */
	protected Entity(Position position) {
		super();
		this.position = position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

}
