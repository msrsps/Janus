/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player.skill;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Skill {

	public static final int ATTACK = 0;
	public static final int DEFENCE = 1;
	public static final int STRENGTH = 2;
	public static final int HITPOINTS = 3;
	public static final int RANGE = 4;
	public static final int PRAYER = 5;
	public static final int MAGIC = 6;
	public static final int COOKING = 7;
	public static final int WOODCUTTING = 8;
	public static final int FLETCHING = 9;
	public static final int FISHING = 10;
	public static final int FIREMAKING = 11;
	public static final int CRAFTING = 12;
	public static final int SMITHING = 13;
	public static final int MINING = 14;
	public static final int HERBLORE = 15;
	public static final int AGILITY = 16;
	public static final int THEIVING = 17;
	public static final int SLAYER = 18;
	public static final int FARMING = 19;
	public static final int RUNECRAFTING = 20;
	public static final int HUNTER = 21;
	public static final int CONSTRUCTION = 22;
	public static final int SUMMONING = 23;
	public static final int DUNGEONEERING = 24;

	/**
	 * The current level of this skill, this can be above and below the actual
	 * level.
	 */
	private int currentLevel = 1;

	/**
	 * The actual level of this skill based on experience, this stays constant
	 * until a player levels up.
	 */
	private int actualLevel = 1;

	/**
	 * The experience in the skill.
	 */
	private double experience = 0;

	/**
	 * New instance.
	 * 
	 * @param currentlevel
	 *            The current level of this skill, this can be above and below
	 *            the actual level.
	 * @param actualLevel
	 *            The actual level of this skill based on experience, this stays
	 *            constant until a player levels up.
	 * @param exp
	 *            The experience in the skill.
	 */
	public Skill(int currentlevel, int actualLevel, double exp) {
		this.currentLevel = currentlevel;
		this.actualLevel = actualLevel;
		this.experience = exp;
	}

	/**
	 * 
	 * @return the experience
	 */
	public double getExperience() {
		return experience;
	}

	/**
	 * @param exp
	 *            the experience to set
	 */
	public void setExperience(double exp) {
		this.experience = exp;
	}

	/**
	 * @return the currentLevel
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel
	 *            the currentLevel to set
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * @return the actualLevel
	 */
	public int getActualLevel() {
		return actualLevel;
	}

	/**
	 * @param actualLevel
	 *            the actualLevel to set
	 */
	public void setActualLevel(int actualLevel) {
		this.actualLevel = actualLevel;
	}

}
