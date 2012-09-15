/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player.skill;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class SkillSet {

	/**
	 * 
	 */
	private final Skill[] skills;

	/**
	 * 
	 */
	public SkillSet() {
		skills = new Skill[25]; // TODO load max skill id from external file.
		for (int index = 0; index < skills.length; index++) {
			if (index == 3)
				skills[index] = new Skill(10, 10, 1154);
			skills[index] = new Skill(1, 1, 0);
		}
	}
}
