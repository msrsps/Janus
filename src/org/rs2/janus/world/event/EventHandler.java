/**
 * 
 */
package org.rs2.janus.world.event;

import org.rs2.janus.world.model.entity.character.player.Player;

/**
 * Handles incoming events from players.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public interface EventHandler<E extends Event> {

	/**
	 * 
	 * @param player
	 * @param event
	 * @throws Exception
	 */
	public void handle(Player player, E event) throws Exception;

}
