/**
 * 
 */
package org.rs2.janus.world.net.login;

import org.rs2.janus.world.model.entity.character.player.PlayerCredientials;
import org.rs2.janus.world.net.Request;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginRequest implements Request {

	/**
	 * 
	 */
	private final PlayerCredientials credientials;

	/**
	 * 
	 */
	private final boolean isLowMemory;

	/**
	 * 
	 */
	private final boolean isReconnecting;

	/**
	 * 
	 */
	private final int namehash;

	/**
	 * 
	 */
	private final int[] archiveCrcs;

	/**
	 * @param isLowMemory
	 * @param isReconnecting
	 * @param namehash
	 * @param archiveCrcs
	 */
	public LoginRequest(PlayerCredientials credientials, boolean isLowMemory, boolean isReconnecting, int namehash, int[] archiveCrcs) {
		this.isLowMemory = isLowMemory;
		this.isReconnecting = isReconnecting;
		this.namehash = namehash;
		this.archiveCrcs = archiveCrcs;
		this.credientials = credientials;
	}

	/**
	 * @return the credientials
	 */
	public PlayerCredientials getCredientials() {
		return credientials;
	}

	/**
	 * @return the isLowMemory
	 */
	public boolean isLowMemory() {
		return isLowMemory;
	}

	/**
	 * @return the isReconnecting
	 */
	public boolean isReconnecting() {
		return isReconnecting;
	}

	/**
	 * @return the namehash
	 */
	public int getNamehash() {
		return namehash;
	}

	/**
	 * @return the archiveCrcs
	 */
	public int[] getArchiveCrcs() {
		return archiveCrcs;
	}

}
