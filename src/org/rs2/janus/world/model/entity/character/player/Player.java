/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.character.Character;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Player extends Character {

	/**
	 * 
	 */
	private final PlayerCredientials credientials;

	/**
	 * @param position
	 */
	public Player(PlayerCredientials credientials, Position position) {
		super(position);
		this.credientials = credientials;
	}

	/**
	 * @return
	 */
	public PlayerCredientials getCredentials() {
		return credientials;
	}

}
