/**
 * 
 */
package org.rs2.janus.world;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.ChannelPipelineFactory;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.net.NioServerNetwork;
import org.rs2.janus.net.Service;
import org.rs2.janus.net.login.LoginService;
import org.rs2.janus.net.world.WorldChannelHandler;
import org.rs2.janus.net.world.WorldPipelineFactory;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class World {

	public static void main(String[] args) {
		try {
			World world = new World();
			world.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private final Service loginService = new LoginService(this);

	/**
	 * 
	 */
	private final WorldChannelHandler worldHandler = new WorldChannelHandler(this);

	/**
	 * 
	 */
	private final ChannelPipelineFactory worldFactory = new WorldPipelineFactory(loginService, worldHandler);

	/**
	 * 
	 */
	private final NioServerNetwork worldNetwork = new NioServerNetwork(Executors.newFixedThreadPool(JanusProperties.getInt("WORLD_NETWORK_THREADS")));

	/**
	 * 
	 */
	private Connection sqlConnection;

	/**
	 * 
	 * @throws Exception
	 */
	protected void init() throws Exception {
		try {
			initSql();
		} catch (Exception e) {
			throw new Exception("Error initiating SQL!", e);
		}

		try {
			initNetwork();
		} catch (Exception e) {
			throw new Exception("Error initiating Network!", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void initNetwork() throws Exception {
		worldNetwork.setPipelineFactory(worldFactory);
		worldNetwork.bind(JanusProperties.getInt("GAME_SERVER_PORT"));
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void initSql() throws Exception {
		sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/rs2", JanusProperties.getString("MYSQL_USER"), JanusProperties
				.getString("MYSQL_PASS"));
	}

	/**
	 * 
	 */
	public Connection getSqlConnection() {
		return sqlConnection;
	}
}
