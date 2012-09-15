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
public class PlayerResetTask extends SynchronizationTask {

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
	public PlayerResetTask(Phaser phaser, Player player) {
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
		player.getBlockSet().clear();
		// TODO Anything else?
	}

}
