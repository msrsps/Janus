/**
 * 
 */
package org.rs2.janus.world.model.item;

/**
 * Represents an item that exists in the world.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Item {

	/**
	 * The item's id.
	 */
	private final int id;

	/**
	 * The amount of items.
	 */
	private final int amount;

	/**
	 * New instance.
	 * 
	 * @param id
	 *            The item's id.
	 * @param amount
	 *            The amount of items.
	 */
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

}
