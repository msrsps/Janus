/**
 * 
 */
package org.rs2.janus.world;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.rs2.janus.world.event.EventManager;
import org.rs2.janus.world.sync.SynchronizationManager;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldEngine implements Runnable {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(WorldEngine.class);

	/**
	 * 
	 */
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	/**
	 * 
	 */
	private final EventManager eventManager = new EventManager();

	/**
	 * 
	 */
	private final SynchronizationManager syncManager;

	/**
	 * 
	 */
	private final WorldContext context;

	/**
	 * 
	 */
	public WorldEngine(WorldContext context) {
		this.context = context;
		this.syncManager = new SynchronizationManager(context);
	}

	/**
	 * 
	 */
	public void init() {
		log.info("Initiating world engine.");

		scheduler.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);

		log.info("World engine successfully initiated.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		eventManager.pulse();
		syncManager.synchronize();

	}

}
