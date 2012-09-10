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
public class PlayerIdleHandler implements PacketHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rs2.janus.net.packet.handle.PacketHandler#handle(org.rs2.janus.world
	 * .model.entity.character.player.Player, org.rs2.janus.net.packet.Packet)
	 */
	@Override
	public void handle(Player player, Packet packet) throws Exception {
		System.out.println("Player idle");
	}

}
