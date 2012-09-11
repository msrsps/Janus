/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

import java.util.concurrent.ArrayBlockingQueue;

import net.burtleburtle.bob.rand.IsaacRandom;

import org.jboss.netty.channel.Channel;
import org.rs2.janus.world.net.packet.Packet;

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
	// Is 10 too big? Too small?
	private final ArrayBlockingQueue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(10);

	/**
	 * 
	 */
	private Player player;

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
	public void send(Packet packet) {
		channel.write(packet);
	}

	/**
	 * 
	 */
	public void receive(Packet packet) {
		packetQueue.add(packet);
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

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

}
