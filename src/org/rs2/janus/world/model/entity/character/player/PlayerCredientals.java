/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;

import org.rs2.janus.util.IsaacRandomPair;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerCredientals {

	/**
	 * 
	 */
	private final String user;

	/**
	 * 
	 */
	private final String pass;

	/**
	 * 
	 */
	private final int uid;

	/**
	 * 
	 */
	private final IsaacRandomPair isaacPair;

	/**
	 * 
	 * @param user
	 * @param pass
	 * @param uid
	 */
	public PlayerCredientals(String user, String pass, int uid, IsaacRandomPair isaacPair) {
		this.user = user;
		this.pass = pass;
		this.uid = uid;
		this.isaacPair = isaacPair;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @return the isaacPair
	 */
	public IsaacRandomPair getIsaacPair() {
		return isaacPair;
	}

}
