/**
 * 
 */
package org.rs2.janus.net.login;

import org.rs2.janus.net.Request;
import org.rs2.janus.world.model.entity.character.player.PlayerCredientals;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginRequest implements Request {

	/**
	 * 
	 */
	private final PlayerCredientals credientials;

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
	public LoginRequest(PlayerCredientals credientials, boolean isLowMemory, boolean isReconnecting, int namehash, int[] archiveCrcs) {
		this.isLowMemory = isLowMemory;
		this.isReconnecting = isReconnecting;
		this.namehash = namehash;
		this.archiveCrcs = archiveCrcs;
		this.credientials = credientials;
	}

	/**
	 * @return the credientials
	 */
	public PlayerCredientals getCredientials() {
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
