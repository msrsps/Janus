/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.character.Character;
import org.rs2.janus.world.model.item.Container;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Player extends Character {

	/**
	 * The player's appearance.
	 */
	private Appearance appearance = Appearance.DEFAULT_APPEARANCE;

	/**
	 * The player's movement animations.
	 */
	private PlayerMovementSet movementSet = PlayerMovementSet.DEFAULT_SET;

	/**
	 * The player's equipment.
	 */
	private final Container equipment = new Container(14);

	/**
	 * The player's inventory.
	 */
	private final Container inventory = new Container(28);

	/**
	 * The player's bank.
	 */
	private final Container bank = new Container(200);

	/**
	 * @param position
	 */
	public Player(Position position) {
		super(position);
	}

	/**
	 * @return the movementSet
	 */
	public PlayerMovementSet getMovementSet() {
		return movementSet;
	}

	/**
	 * @param movementSet
	 *            the movementSet to set
	 */
	public void setMovementSet(PlayerMovementSet movementSet) {
		this.movementSet = movementSet;
	}

	/**
	 * @return the equipment
	 */
	public Container getEquipment() {
		return equipment;
	}

	/**
	 * @return the inventory
	 */
	public Container getInventory() {
		return inventory;
	}

	/**
	 * @return the bank
	 */
	public Container getBank() {
		return bank;
	}

	/**
	 * @return the appearance
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * @param appearance
	 *            the appearance to set
	 */
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

}
