/**
 * 
 */
package org.rs2.janus.world.model.entity.character;

import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.character.player.PlayerCredientals;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Player extends Character {

	/**
	 * 
	 */
	private final PlayerCredientals credientals;

	/**
	 * @param position
	 */
	protected Player(PlayerCredientals credientals, Position position) {
		super(position);
		this.credientals = credientals;
	}

}
