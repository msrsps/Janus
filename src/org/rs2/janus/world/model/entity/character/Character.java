/**
 * 
 */
package org.rs2.janus.world.model.entity.character;

import java.util.HashSet;
import java.util.Set;

import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.Entity;
import org.rs2.janus.world.model.entity.character.npc.Npc;
import org.rs2.janus.world.sync.block.SynchronizationBlockSet;

/**
 * A character is an {@link Entity} that is mobile, intractable, combatable, and
 * must be synchronized with player using "character updating".
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Character extends Entity {

	/**
	 * The character's synchronization blocks.
	 */
	private final SynchronizationBlockSet blockSet = new SynchronizationBlockSet();

	/**
	 * The players around this character.
	 */
	private final Set<Character> localPlayers = new HashSet<Character>();

	/**
	 * The npcs around this character.
	 */
	private final Set<Npc> localNpcs = new HashSet<Npc>();

	/**
	 * The character's walking queue.
	 */
	private final WalkingQueue walkingQueue = new WalkingQueue(this);

	/**
	 * The position that the last region change took place.
	 */
	private Position lastLoad;

	/**
	 * The character's index in the world list.
	 */
	private int index = -1;

	/**
	 * New instance.
	 * 
	 * @param position
	 *            The current position of this character.
	 */
	protected Character(Position position) {
		super(position);
	}

	/**
	 * @return the blockSet
	 */
	public SynchronizationBlockSet getBlockSet() {
		return blockSet;
	}

	/**
	 * @return the localNpcs
	 */
	public Set<Npc> getLocalNpcs() {
		return localNpcs;
	}

	/**
	 * @return the localPlayers
	 */
	public Set<Character> getLocalPlayers() {
		return localPlayers;
	}

	/**
	 * @return the walkingQueue
	 */
	public WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	/**
	 * @return the lastLoad
	 */
	public Position getLastLoad() {
		return lastLoad;
	}

	/**
	 * @param lastLoad
	 *            the lastLoad to set
	 */
	public void setLastLoad(Position lastLoad) {
		this.lastLoad = lastLoad;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
