/**
 * 
 */
package org.rs2.janus.world.model.entity.character;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WalkingQueue {

	/**
	 * The character that this queue controls.
	 */
	private final Character character;

	/**
	 * New instance.
	 * 
	 * @param character
	 *            The character that this queue controls.
	 */
	public WalkingQueue(Character character) {
		this.character = character;
	}

	/**
	 * "Pulses" the walking queue.
	 */
	public void pulse() {

	}

}
