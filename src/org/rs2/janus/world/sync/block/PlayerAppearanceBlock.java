/**
 * 
 */
package org.rs2.janus.world.sync.block;

import org.rs2.janus.world.model.entity.character.player.Player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerAppearanceBlock extends SynchronizationBlock {

	/**
	 * 
	 */
	public static PlayerAppearanceBlock create(Player player) {
		return new PlayerAppearanceBlock();
	}

	/**
	 * 
	 */
	private PlayerAppearanceBlock() {
		// TODO Auto-generated constructor stub
	}

}
