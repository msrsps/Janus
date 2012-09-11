/**
 * 
 */
package org.rs2.janus.world;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.net.NioServerNetwork;
import org.rs2.janus.world.net.Service;
import org.rs2.janus.world.net.WorldChannelHandler;
import org.rs2.janus.world.net.WorldPipelineFactory;
import org.rs2.janus.world.net.login.LoginRequest;
import org.rs2.janus.world.net.login.LoginService;
import org.rs2.janus.world.net.ondemand.OnDemandRequest;
import org.rs2.janus.world.net.ondemand.OnDemandService;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class World {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(World.class);

	/**
	 * 
	 */
	private final Map<Class, Service> services = new HashMap<Class, Service>();

	/**
	 * 
	 */
	private final WorldContext context = new WorldContext(this);

	/**
	 * 
	 */
	private final NioServerNetwork network = new NioServerNetwork(Executors.newFixedThreadPool(JanusProperties.getInt("WORLD_NETWORK_THREADS")));

	/**
	 * 
	 */
	private final WorldEngine engine = new WorldEngine(context);

	/**
	 * 
	 */
	private void init() {
		log.info("Initiating world.");

		log.info("Initiating services.");
		initServices();

		log.info("Initiating network.");
		initNetwork();

		log.info("World successfully initiated.");
	}

	private void initServices() {
		services.put(LoginRequest.class, LoginService.getSingleton());
		services.put(OnDemandRequest.class, OnDemandService.getSingleton());
	}

	private void initNetwork() {
		try {
			WorldChannelHandler channelHandler = new WorldChannelHandler(context);
			WorldPipelineFactory pipelineFactory = new WorldPipelineFactory(channelHandler);

			network.setPipelineFactory(pipelineFactory);
			network.bind(JanusProperties.getInt("GAME_SERVER_PORT"));
		} catch (Exception e) {
			throw new RuntimeException("Error initiating network.", e);
		}
	}

	public static void main(String[] args) {
		try {
			World world = new World();
			world.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the engine
	 */
	public WorldEngine getEngine() {
		return engine;
	}

	/**
	 * @return the services
	 */
	public Map<Class, Service> getServices() {
		return services;
	}

}
