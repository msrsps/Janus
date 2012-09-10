package org.rs2.janus.net.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.net.Service;
import org.rs2.janus.net.login.LoginResponse.LoginResponseCode;
import org.rs2.janus.world.World;
import org.rs2.janus.world.model.Position;
import org.rs2.janus.world.model.entity.character.player.Player;

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
	private final ExecutorService executor = Executors.newFixedThreadPool(JanusProperties.getInt("LOGIN_SERVICE_THREADS"));

	/**
	 * 
	 */
	private final World world;

	/**
	 * 
	 */
	public LoginService(World world) {
		this.world = world;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rs2.janus.net.service.Service#serviceRequest(org.jboss.netty.channel
	 * .ChannelHandlerContext, org.rs2.janus.net.request.Request)
	 */
	@Override
	public void serviceRequest(ChannelHandlerContext ctx, LoginRequest request) throws Exception {
		executor.execute(new Worker(ctx.getChannel(), request));
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
		private final Connection sqlConnection = world.getSqlConnection();

		/**
		 * 
		 */

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
						channel.write(new LoginResponse(LoginResponseCode.SUCCESSFUL, 0, 0));
						channel.setAttachment(new Player(request.getCredientials(), new Position(result.getInt("X"), result.getInt("Y"), result
								.getInt("Height"))));
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
