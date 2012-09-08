/**
 * 
 */
package org.rs2.janus.world;

import java.util.concurrent.Executors;

import org.jboss.netty.channel.ChannelPipelineFactory;
import org.rs2.janus.JanusProperties;
import org.rs2.janus.net.NioServerNetwork;
import org.rs2.janus.net.Service;
import org.rs2.janus.net.login.LoginService;
import org.rs2.janus.net.world.WorldChannelHandler;
import org.rs2.janus.net.world.WorldPipelineFactory;
import org.rs2.janus.util.LimitedMap;
import org.rs2.janus.world.model.entity.character.Player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class World {

	public static void main(String[] args) {
		try {
			new World().initNetwork();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	private final WorldChannelHandler worldHandler = new WorldChannelHandler();

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
	private final LimitedMap<String, Player> worldPlayers = new LimitedMap<String, Player>(2000);

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
	 * @param user
	 * @return
	 */
	public Player getPlayer(String user) {
		return worldPlayers.get(user);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Player addPlayer(String user, Player player) {
		return worldPlayers.put(user, player);
	}

}
