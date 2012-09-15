/**
 * 
 */
package org.rs2.janus.world.sync.task;

import java.util.concurrent.Phaser;

import org.rs2.janus.world.model.entity.character.player.Player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerSynchronizationTask extends SynchronizationTask {

	/**
	 * The player that will be synchronized.
	 */
	private final Player player;

	/**
	 * New instance.
	 * 
	 * @param phaser
	 *            The phaser that this task will have to unregister from.
	 * @param player
	 *            The player that will be synchronized.
	 */
	public PlayerSynchronizationTask(Phaser phaser, Player player) {
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
		// TODO Update local player list.
		// TODO Update local npc list.
		// TODO Write player blocks.
		// TODO Write npc blocks.
		// TODO Objects?
		// TODO Ground items?
	}

}
