/**
 * 
 */
package org.rs2.janus.world.model.entity.character.player;


/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PlayerCredientials {

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
	 * @param user
	 * @param pass
	 * @param uid
	 */
	public PlayerCredientials(String user, String pass, int uid) {
		this.user = user;
		this.pass = pass;
		this.uid = uid;
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

}
