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
public class SilentPacketHandler implements PacketHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rs2.janus.net.packet.handle.PacketHandler#handle(org.rs2.janus.world
	 * .model.entity.character.player.Player, org.rs2.janus.net.packet.Packet)
	 */
	@Override
	public void handle(Player player, Packet packet) throws Exception {
		/* Empty */
	}

}
