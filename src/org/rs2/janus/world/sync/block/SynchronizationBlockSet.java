/**
 * 
 */
package org.rs2.janus.world.sync.block;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A set of synchronization blocks that a character has.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class SynchronizationBlockSet {

	/**
	 * The set of blocks.
	 */
	private final Map<Class<? extends SynchronizationBlock>, SynchronizationBlock> blocks = new HashMap<Class<? extends SynchronizationBlock>, SynchronizationBlock>();

	/**
	 * Puts a block into this set.
	 * 
	 * @param block
	 *            The block to put into the set.
	 */
	public void put(SynchronizationBlock block) {
		blocks.put(block.getClass(), block);
	}

	/**
	 * Removes a specific type of block from the set.
	 * 
	 * @param type
	 *            The type of block to remove.
	 */
	public void remove(Class<? extends SynchronizationBlock> type) {
		blocks.remove(type);
	}

	/**
	 * Removes all of the blocks from this set.
	 */
	public void clear() {
		blocks.clear();
	}

	/**
	 * Copies the blocks from this set and put's them into a new set, any
	 * changes made to this set do not affect the other.
	 */
	public SynchronizationBlockSet copy() {
		SynchronizationBlockSet newBlockSet = new SynchronizationBlockSet();
		newBlockSet.blocks.putAll(blocks);
		return newBlockSet;
	}

	/**
	 * Get's the blocks in this set.
	 */
	public Collection<SynchronizationBlock> getBlocks() {
		return blocks.values();
	}

}
