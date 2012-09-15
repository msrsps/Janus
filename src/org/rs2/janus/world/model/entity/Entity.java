/**
 * 
 */
package org.rs2.janus.world.model.entity;

import org.rs2.janus.world.model.Position;

/**
 * Represents an object within the world which has a physically position.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Entity {

	/**
	 * The current position of the entity.
	 */
	private Position position;

	/**
	 * New instance.
	 * 
	 * @param position
	 *            The current position of the entity.
	 */
	protected Entity(Position position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

}
