/**
 * 
 */
package org.rs2.janus.world.net.packet;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.rs2.janus.world.net.packet.handle.PacketHandler;
import org.rs2.janus.world.net.packet.handle.SilentPacketHandler;

import com.google.gson.Gson;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketMeta {

	/**
	 * 
	 */
	private static final Map<Integer, PacketHandler> packetHandlers = new HashMap<Integer, PacketHandler>();

	/**
	 * 
	 */
	private static final PacketHandler silentHandler = new SilentPacketHandler();

	/**
	 * 
	 */
	public int opcode;

	/**
	 * 
	 */
	public String handler;

	/**
	 * Loads the packet meta.
	 */
	protected static void load() throws Exception {
		Gson gson = new Gson();
		PacketMeta[] metaSet = gson.fromJson(new FileReader("data/PacketMeta.json"), PacketMeta[].class);

		for (PacketMeta meta : metaSet) {
			Class clazz = Class.forName("org.rs2.janus.world.net.packet.handle." + meta.handler);
			packetHandlers.put(meta.opcode, (PacketHandler) clazz.newInstance());
		}
	}

	/**
	 * Gets a packet's handler.
	 * 
	 * @param packetOpcode
	 *            The packet's opcode.
	 * @return The handler, a silent handler of none was found.
	 */
	public static final PacketHandler getHandler(int packetOpcode) {
		PacketHandler handler = packetHandlers.get(packetOpcode);
		return handler == null ? silentHandler : handler;
	}
}
