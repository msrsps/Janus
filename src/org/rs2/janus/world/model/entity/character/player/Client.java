/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

import net.burtleburtle.bob.rand.IsaacRandom;

import org.jboss.netty.channel.Channel;
import org.rs2.janus.net.packet.Packet;

/**
 * Holds all network information of a client that's connected to a world. This
 * works as an interface separating the network and a player.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Client {

	/**
	 * 
	 */
	private final Channel channel;

	/**
	 * 
	 */
	private final IsaacRandom encoder;

	/**
	 * 
	 */
	private final IsaacRandom decoder;

	/**
	 * 
	 */
	public Client(Channel channel, IsaacRandom encoder, IsaacRandom decoder) {
		this.channel = channel;
		this.encoder = encoder;
		this.decoder = decoder;
	}

	/**
	 * Sends a packet to the client.
	 * 
	 * @param packet
	 *            The packet to send.
	 */
	public void write(Packet packet) {
		channel.write(packet);
	}

	/**
	 * @return the encoder
	 */
	public IsaacRandom getEncoder() {
		return encoder;
	}

	/**
	 * @return the decoder
	 */
	public IsaacRandom getDecoder() {
		return decoder;
	}

}