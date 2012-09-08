/**
 * 
 */
package org.rs2.janus.net.login;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginResponse {

	/**
	 * 
	 */
	private final LoginResponseCode responseCode;

	/**
	 * 
	 */
	private int flagged;

	/**
	 * 
	 */
	private int privilegeLevel;

	/**
	 * 
	 */
	public LoginResponse(LoginResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @param responseCode
	 */
	public LoginResponse(LoginResponseCode responseCode, int privilegeLevel, int flagged) {
		this(responseCode);
		this.privilegeLevel = privilegeLevel;
		this.flagged = flagged;
	}

	/**
	 * @return the responseCode
	 */
	public LoginResponseCode getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the flagged
	 */
	public int getFlagged() {
		return flagged;
	}

	/**
	 * @return the privilegeLevel
	 */
	public int getPrivilegeLevel() {
		return privilegeLevel;
	}

	/**
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	public enum LoginResponseCode {

		SUCCESSFUL(2),
		INVALID_CREDENTIALS(3),
		ACCOUNT_DISABLED(4),
		ACCOUNT_ONLINE(5),
		WORLD_UPDATED(6),
		WORLD_FULL(7),
		LOGIN_SERVER_OFFLINE(8),
		LOGIN_LIMIT_EXCEEDED(9),
		BAD_SESSION_ID(10),
		PLEASE_TRY_AGAIN(11),
		MEMBERS_WORLD(12),
		UNSUCCESSFULY(13),
		WORLD_UPDATING(14),
		LOGIN_ATTEMPTS_EXCEEDED(16),
		MEMBERS_ONLY_AREA(17);

		/**
		 * New instance.
		 * 
		 * @param code
		 *            The code that matches the corresponding response.
		 */
		private LoginResponseCode(int code) {
			this.code = code;
		}

		/**
		 * The code that matches the corresponding response.
		 */
		private int code;

		/**
		 * @return the code
		 */
		public int getCode() {
			return code;
		}

	}

}
