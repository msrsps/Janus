/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Appearance {

	// Gender values.
	public static final int MALE = 0;
	public static final int FEMALE = 1;

	// Style indexes.
	public static final int HEAD = 0;
	public static final int FACE = 1;
	public static final int CHEST = 2;
	public static final int ARMS = 3;
	public static final int HANDS = 4;
	public static final int LEGS = 5;
	public static final int FEET = 6;

	/**
	 * The default appearance.
	 */
	public static final Appearance DEFAULT_APPEARANCE = new Appearance(MALE, new int[] { 0, 10, 18, 26, 33, 36, 42 }, new int[5]);

	/**
	 * 
	 */
	private final int gender;

	/**
	 * 
	 */
	private final int[] styles;

	/**
	 * 
	 */
	private final int[] colors;

	/**
	 * 
	 */
	public Appearance(int gender, int[] styles, int[] colors) {
		if (gender != 0 && gender != 1)
			throw new IllegalArgumentException("Gender must be 0 (Male) or 1 (Female).");

		if (styles.length != 7)
			throw new IllegalArgumentException("Styles must have 7 styles in it.");

		if (colors.length != 5)
			throw new IllegalArgumentException("Colors must have 5 colors in it.");

		this.gender = gender;
		this.styles = styles;
		this.colors = colors;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @return the styles
	 */
	public int[] getStyles() {
		return styles;
	}

	/**
	 * @return the colors
	 */
	public int[] getColors() {
		return colors;
	}

}
