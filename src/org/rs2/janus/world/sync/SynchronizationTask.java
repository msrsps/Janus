/**
 * 
 */
package org.rs2.janus.world.sync;

import java.util.concurrent.Phaser;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public abstract class SynchronizationTask implements Runnable {

	/**
	 * 
	 */
	private final Phaser phaser;

	/**
	 * 
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
		synchronize();
		phaser.arriveAndDeregister();
	}

	/**
	 * 
	 */
	protected abstract void synchronize();

}
