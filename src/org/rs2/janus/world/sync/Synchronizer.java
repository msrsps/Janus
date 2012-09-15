/**
 * 
 */
package org.rs2.janus.world.sync;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.rs2.janus.world.World;
import org.rs2.janus.world.model.entity.character.npc.Npc;
import org.rs2.janus.world.model.entity.character.player.Player;
import org.rs2.janus.world.sync.task.NpcMovementTask;
import org.rs2.janus.world.sync.task.PlayerMovementTask;
import org.rs2.janus.world.sync.task.PlayerResetTask;
import org.rs2.janus.world.sync.task.PlayerSynchronizationTask;
import org.rs2.janus.world.sync.task.SynchronizationTask;

/**
 * Keeps a world synchronized by running tasks in a specific order, these tasks
 * update every important object and keeps everything running smoothly for the
 * players.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Synchronizer {

	/**
	 * The world that that will be synchronized.
	 */
	private final World world;

	/**
	 * The {@link ExecutorService} that will execute the
	 * {@link SynchronizationTask}s.
	 */
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * The {@link Phaser} that will keep the synchronizer out of trouble (do 1
	 * type of task at a time, not skipping ahead of it's self).
	 */
	private final Phaser phaser = new Phaser(1);

	/**
	 * New instance.
	 * 
	 * @param world
	 *            The world that that will be synchronized.
	 */
	public Synchronizer(World world) {
		this.world = world;
	}

	/**
	 * Run every "pulse" it keeps the world synchronized by completing various
	 * tasks.
	 */
	public void pulse() {
		Collection<Npc> npcs = world.getGlobalNpcs();
		Collection<Player> players = world.getGlobalPlayers().values();

		// Update character movements.
		phaser.bulkRegister(players.size());
		for (Player player : players)
			executor.execute(new PlayerMovementTask(phaser, player));

		phaser.bulkRegister(npcs.size());
		for (Npc npc : npcs)
			executor.execute(new NpcMovementTask(phaser, npc));
		phaser.arriveAndAwaitAdvance();

		// Send player synchronization events.
		phaser.bulkRegister(players.size());
		for (Player player : players)
			executor.execute(new PlayerSynchronizationTask(phaser, player));
		phaser.arriveAndAwaitAdvance();

		// Reset players
		phaser.bulkRegister(players.size());
		for (Player player : players)
			executor.execute(new PlayerResetTask(phaser, player));
		phaser.arriveAndAwaitAdvance();

	}
}
