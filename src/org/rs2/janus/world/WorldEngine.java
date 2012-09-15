/**
 * 
 */
package org.rs2.janus.world;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.rs2.janus.world.sync.Synchronizer;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldEngine implements Runnable {

	/**
	 * 
	 */
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	/**
	 * 
	 */
	private final Synchronizer synchronizer;

	/**
	 * 
	 */
	private final World world;

	/**
	 * 
	 */
	public WorldEngine(World world) {
		this.world = world;
		this.synchronizer = new Synchronizer(world);
	}

	/**
	 * 
	 */
	public void init() {
		scheduler.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		synchronizer.pulse();

	}

}
