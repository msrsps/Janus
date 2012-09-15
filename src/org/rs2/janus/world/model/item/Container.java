/**
 * 
 */
package org.rs2.janus.world.model.item;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Container {

	/**
	 * The capacity of the container.
	 */
	private final int capacity;

	/**
	 * The items held in the container.
	 */
	private final Item[] items;

	/**
	 * New instance.
	 * 
	 * @param capacity
	 *            The capacity of the container.
	 */
	public Container(int capacity) {
		this.items = new Item[capacity];
		this.capacity = capacity;
	}

	/**
	 * Adds an item at the next available slot, if there is one.
	 * 
	 * @param item
	 *            The item to add.
	 * @return If the item was added.
	 */
	public boolean add(Item item) {
		for (int slot = 0; slot < capacity; slot++) {
			try {
				if (add(item, slot))
					return true;
			} catch (ArrayIndexOutOfBoundsException e) {
				notifyFull();
				return false;
			}
		}
		return false;
	}

	/**
	 * Attempts to add an item to a slot.
	 * 
	 * @param item
	 *            The item to add.
	 * @param slot
	 *            The slot to add the item at.
	 * @return If the item was added.
	 */
	public boolean add(Item item, int slot) {
		Item tmp = items[slot];
		if (tmp != null) {
			if (tmp.getId() == item.getId()) {
				if (true) { // FIXME Check if stackable!
					items[slot] = new Item(item.getId(), tmp.getAmount() + item.getAmount());
					return true;
				}
			}
		} else {
			items[slot] = item;
			return true;
		}
		return false;
	}

	/**
	 * Sets a slot with an item. This will ignore stacking.
	 * 
	 * @param item
	 *            The item to set.
	 * @param slot
	 *            The slot to set at.
	 */
	public void set(Item item, int slot) {
		items[slot] = item;
	}

	/**
	 * Resets a slot by setting it to null.
	 * 
	 * @param slot
	 *            The slot to reset.
	 */
	public void reset(int slot) {
		items[slot] = null;
	}

	/**
	 * Retrieve the item at a slot.
	 * 
	 * @param slot
	 *            The slot to retrieve from.
	 * @return The item that was retrieved.
	 */
	public Item get(int slot) {
		return items[slot];
	}

	/**
	 * Removes an amount of items from container.
	 * 
	 * @param item
	 *            The items to remove.
	 * @return If the items were removed, this should always be true if
	 *         <code> amountOf(item.getAmount()) <= item.getAmount() </code> is
	 *         true.
	 */
	public boolean remove(Item item) {
		if (amountOf(item.getAmount()) <= item.getAmount())
			return false;

		int removed = 0;
		for (int slot = 0; slot < capacity; slot++) {
			Item tmp = items[slot];
			if (item != null && tmp.getId() == item.getId()) {
				int amountToGo = item.getAmount() - removed;
				if (tmp.getAmount() < amountToGo) {
					items[slot] = null;
					removed += tmp.getAmount();
				} else {
					if (tmp.getAmount() == amountToGo)
						items[slot] = null;
					else
						items[slot] = new Item(item.getId(), tmp.getAmount() - amountToGo);
					return true;
				}
			}
		}
		return false; // Should never happen due to first check.
	}

	/**
	 * Checks if the container contains an item.
	 * 
	 * @param itemId
	 *            the item to look for.
	 * @return If the container contains the item.
	 */
	public boolean contains(int itemId) {
		for (Item item : items)
			if (item != null && item.getId() == itemId)
				return true;
		return false;
	}

	/**
	 * Gets the amount of items with the given id.
	 * 
	 * @param item
	 *            The item to look for.
	 * @return The amount of that item this contain holds.
	 */
	public int amountOf(int itemId) {
		int amount = 0;
		for (Item item : items)
			if (item != null && item.getId() == itemId)
				amount += item.getAmount();
		return amount;
	}

	/**
	 * Clears this container of all items by setting all of the slots to null.
	 */
	public void clear() {
		for (int slot = 0; slot < capacity; slot++)
			items[slot] = null;
	}

	/**
	 * Creates a copy of this container.
	 */
	public Container copy() {
		Container copy = new Container(capacity);
		System.arraycopy(items, 0, copy.items, 0, capacity);
		return copy;
	}

	/**
	 * Notifies that the container is full, this can be override for handling,
	 * it does nothing by default.
	 */
	protected void notifyFull() {
		// Unhandled by default.
	}
}
