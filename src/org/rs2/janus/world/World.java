/**
 * 
 */
package org.rs2.janus.world;

import java.util.ArrayList;

import org.apache.commons.collections.map.ListOrderedMap;
import org.rs2.janus.world.model.entity.character.npc.Npc;
import org.rs2.janus.world.model.entity.character.player.Player;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class World {

	/**
	 * 
	 */
	private final WorldEngine engine = new WorldEngine(this);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	// FIXME gotta protect this from concurrency issues when players log in!
	private final ListOrderedMap globalPlayers = new ListOrderedMap();

	/**
	 * 
	 */
	private final ArrayList<Npc> globalNpcs = new ArrayList<Npc>();

	/**
	 * @return the globalPlayers
	 */
	public ListOrderedMap getGlobalPlayers() {
		return globalPlayers;
	}

	/**
	 * @return the globalNpcs
	 */
	public ArrayList<Npc> getGlobalNpcs() {
		return globalNpcs;
	}

	/**
	 * 
	 */
	public boolean addPlayer(String name, Player player) {
		if (globalPlayers.size() >= 2000) // TODO Load max players from
											// properties.
			return false;

		globalPlayers.put(name, player);
		player.setIndex(globalPlayers.indexOf(name));
		return true;
	}

	/**
	 * 
	 */
	public void removePlayer(String name) {
		Player player = (Player) globalPlayers.remove(name);
		player.setIndex(-1);

	}

	/**
	 * 
	 */
	public void removePlayer(Player player) {
		globalPlayers.remove(player.getIndex());
		player.setIndex(-1);
	}

	/**
	 * 
	 */
	public void addNpc(Npc npc) {
		globalNpcs.add(npc);
		npc.setIndex(globalNpcs.indexOf(npc));
	}

	/**
	 * 
	 */
	public void removeNpc(Npc npc) {
		globalNpcs.remove(npc.getIndex());
		npc.setIndex(-1);
	}
}
