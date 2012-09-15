/**
 * 
 */
package org.rs2.janus.world.sync.task;

import java.util.concurrent.Phaser;

import org.rs2.janus.world.model.entity.character.player.Player;

/**
 * Synchronizes a player's movements checking if they need to change chunks
 * and/or regions.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerMovementTask extends SynchronizationTask {

	/**
	 * The player that will be synchronized.
	 */
	private final Player player;

	/**
	 * New instance.
	 * 
	 * @param phaser
	 *            The world that that will be synchronized.
	 * @param player
	 *            The player that will be synchronized.
	 */
	public PlayerMovementTask(Phaser phaser, Player player) {
		super(phaser);
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rs2.janus.world.sync.task.SynchronizationTask#pulse()
	 */
	@Override
	public void pulse() {
		player.getWalkingQueue().pulse();

		// TODO Region changing.
		// TODO Teleporting.
		// TODO Others?
	}
}
