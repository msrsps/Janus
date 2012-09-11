package org.rs2.janus.world.net.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.world.WorldContext;
import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.character.player.Client;
import org.rs2.janus.world.model.entity.character.player.Player;
import org.rs2.janus.world.net.Service;
import org.rs2.janus.world.net.login.LoginResponse.LoginResponseCode;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginService implements Service<LoginRequest> {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(LoginService.class);

	/**
	 * 
	 */
	private static final LoginService singleton = new LoginService();

	/**
	 * The executor that will execute workers.
	 */
	private final ExecutorService executor = Executors.newFixedThreadPool(JanusProperties.getInt("LOGIN_SERVICE_THREADS"));

	/**
	 * 
	 */
	private static final Connection sqlConnection;

	static {
		try {
			sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/rs2", JanusProperties.getString("MYSQL_USER"), JanusProperties
					.getString("MYSQL_PASS"));
		} catch (Exception e) {
			throw new RuntimeException("Error connecting to SQL database.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rs2.janus.world.net.Service#messageReceived(org.rs2.janus.world.
	 * WorldContext, org.jboss.netty.channel.Channel,
	 * org.rs2.janus.world.net.Request)
	 */
	@Override
	public void messageReceived(WorldContext context, Channel channel, LoginRequest request) {
		executor.execute(new Worker(channel, request));
	}

	/**
	 * @return the singleton
	 */
	public static LoginService getSingleton() {
		return singleton;
	}

	/**
	 * 
	 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
	 * 
	 */
	private class Worker implements Runnable {

		/**
		 * The request that the worker will process.
		 */
		private final LoginRequest request;

		/**
		 * 
		 */
		private final Channel channel;

		/**
		 * 
		 */
		public Worker(Channel channel, LoginRequest request) {
			this.request = request;
			this.channel = channel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				Statement statement = sqlConnection.createStatement();
				statement.execute("SELECT * FROM characters WHERE User ='" + request.getCredientials().getUser() + "' LIMIT 1");
				ResultSet result = statement.getResultSet();
				if (result.first()) {
					if (request.getCredientials().getPass().equals(result.getString("Pass"))) {
						System.out.println("Successful.");
						channel.write(new LoginResponse(LoginResponseCode.SUCCESSFUL, result.getInt("Privilage"), result.getInt("Flagged")));
						((Client) channel.getAttachment()).setPlayer(new Player(request.getCredientials(), new Position(result.getInt("X"), result
								.getInt("Y"), result.getInt("Height"))));
					} else {
						System.out.println("Wrong pass.");
						channel.write(new LoginResponse(LoginResponseCode.INVALID_CREDENTIALS));
					}
				} else {
					System.out.println("Unknown user.");
					channel.write(new LoginResponse(LoginResponseCode.INVALID_CREDENTIALS));
				}
			} catch (Exception e) {
				System.out.println("Exception");
				channel.write(new LoginResponse(LoginResponseCode.PLEASE_TRY_AGAIN));
				e.printStackTrace();
			}
		}
	}
}
