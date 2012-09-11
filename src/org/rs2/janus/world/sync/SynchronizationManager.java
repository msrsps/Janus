/**
 * 
 */
package org.rs2.janus.world.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.rs2.janus.world.WorldContext;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class SynchronizationManager {

	/**
	 * 
	 */
	private final WorldContext context;

	/**
	 * 
	 */
	private final Phaser phaser = new Phaser(1);

	/**
	 * 
	 */
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * 
	 */
	public SynchronizationManager(WorldContext context) {
		this.context = context;
	}

	/**
	 * 
	 */
	public void synchronize() {

	}

}
