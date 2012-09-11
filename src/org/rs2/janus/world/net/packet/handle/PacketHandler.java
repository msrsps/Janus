/**
 * 
 */
package org.rs2.janus.world.net.packet.handle;

import org.rs2.janus.world.model.entity.character.player.Player;
import org.rs2.janus.world.net.packet.Packet;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public interface PacketHandler {

	/**
	 * Handles a player's packet.
	 * 
	 * @param player
	 *            The player who received the packet.
	 * @param packet
	 *            The packet that was recieved.
	 * @throws Exception
	 *             If an exception occures during handling.
	 */
	public void handle(Player player, Packet packet) throws Exception;

}
