/**
 * 
 */
package org.rs2.janus.world.sync.task;

import java.util.concurrent.Phaser;

import org.rs2.janus.world.sync.Synchronizer;

/**
 * A task that the {@link Synchronizer} must run each "pulse" in order to keep
 * the world synchronized.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public abstract class SynchronizationTask implements Runnable {

	/**
	 * The phaser that this task will have to unregister from.
	 */
	private final Phaser phaser;

	/**
	 * New instance.
	 * 
	 * @param phaser
	 *            The phaser that this task will have to unregister from.
	 */
	public SynchronizationTask(Phaser phaser) {
		this.phaser = phaser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			pulse();
		} finally {
			phaser.arriveAndDeregister();
		}

	}

	/**
	 * "Pulses" the synchronization task.
	 */
	public abstract void pulse();

}
