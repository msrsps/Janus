/**
 * 
 */
package org.rs2.janus.world;

import org.rs2.janus.world.net.Service;

/**
 * Works as a the interface between a world and it's interal workings.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class WorldContext {

	/**
	 * The world that this will protect.
	 */
	private final World world;

	/**
	 * New instance.
	 * 
	 * @param world
	 *            The world that this will protect.
	 */
	public WorldContext(World world) {
		this.world = world;
	}

	/**
	 * 
	 */
	public WorldEngine getEngine() {
		return world.getEngine();
	}

	/**
	 * 
	 * @param service
	 * @return
	 */
	public Service getService(Class type) {
		return world.getServices().get(type);
	}

}
