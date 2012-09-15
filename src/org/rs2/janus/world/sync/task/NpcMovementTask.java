/**
 * 
 */
package org.rs2.janus.world.sync.task;

import java.util.concurrent.Phaser;

import org.rs2.janus.world.model.entity.character.npc.Npc;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class NpcMovementTask extends SynchronizationTask {

	/**
	 * 
	 */
	private final Npc npc;

	/**
	 * @param phaser
	 */
	public NpcMovementTask(Phaser phaser, Npc npc) {
		super(phaser);
		this.npc = npc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rs2.janus.world.sync.task.SynchronizationTask#pulse()
	 */
	@Override
	public void pulse() {
		npc.getWalkingQueue().pulse();

	}

}
