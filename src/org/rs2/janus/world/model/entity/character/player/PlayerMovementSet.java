/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerMovementSet {

	// Movement indexes.
	public static final int STAND = 0;
	public static final int STAND_TURN = 1;
	public static final int WALK = 2;
	public static final int TURN_180 = 3;
	public static final int TURN_CW = 4;
	public static final int TURN_CCW = 5;
	public static final int RUN = 6;

	/**
	 * The default movement set (No equipment).
	 */
	public static final PlayerMovementSet DEFAULT_SET = new PlayerMovementSet(new int[] { 0x328, 0x337, 0x333, 0x334, 0x335, 0x336, 0x338 });

	/**
	 * An array with all of the movement animations.
	 */
	private final int[] animations;

	/**
	 * 
	 */
	public PlayerMovementSet(int[] animations) {
		if (animations.length != 7)
			throw new IllegalArgumentException("The animations must contain 7 animations.");

		this.animations = animations;
	}

	/**
	 * @return the animations
	 */
	public int[] getAnimations() {
		return animations;
	}

}
