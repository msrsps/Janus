/**
 * 
 */
package org.rs2.janus;

import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.rs2.janus.net.NioServerNetwork;
import org.rs2.janus.net.world.WorldChannelHandler;
import org.rs2.janus.net.world.WorldPipelineFactory;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldServer {

	public static void main(String[] args) {
		new WorldServer().init();
	}

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(WorldServer.class);

	/**
	 *
	 */
	private final NioServerNetwork worldNetwork = new NioServerNetwork(Executors.newFixedThreadPool(JanusProperties.getInt("GAME_NETWORK_THREADS")));

	/**
	 * 
	 */
	private final WorldChannelHandler worldHandler = new WorldChannelHandler();

	/**
	 * 
	 */
	private final ChannelPipelineFactory pipelineFactory = new WorldPipelineFactory(worldHandler);

	/**
	 * 
	 */
	public WorldServer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public void init() {
		initWorld();
	}

	/**
	 * 
	 */
	private boolean initWorld() {
		log.info("Initiating World Network.");

		worldNetwork.setPipelineFactory(pipelineFactory);

		try {
			worldNetwork.bind(JanusProperties.getInt("GAME_SERVER_PORT"));
		} catch (Exception e) {
			log.error("Error binding World Network.", e);
			return false;
		}
		return true;
	}

}
