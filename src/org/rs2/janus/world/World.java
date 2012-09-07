/**
 * 
 */
package org.rs2.janus.world;

import org.jboss.netty.channel.ChannelPipelineFactory;
import org.rs2.janus.net.service.Service;
import org.rs2.janus.net.world.WorldChannelHandler;
import org.rs2.janus.net.world.WorldPipelineFactory;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class World {

	/**
	 * 
	 */
	private final Service loginService = null;

	/**
	 * 
	 */
	private final WorldChannelHandler worldHandler = new WorldChannelHandler();

	/**
	 * 
	 */
	private final ChannelPipelineFactory worldFactory = new WorldPipelineFactory(loginService, worldHandler);

}
