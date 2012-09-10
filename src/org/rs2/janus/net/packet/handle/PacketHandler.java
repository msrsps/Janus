/**
 * 
 */
package org.rs2.janus.net.packet.handle;

import org.rs2.janus.net.packet.Packet;
import org.rs2.janus.world.model.entity.character.player.Player;

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
