/**
 * 
 */
package org.rs2.janus.net.packet;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.rs2.janus.net.packet.handle.PacketHandler;
import org.rs2.janus.net.packet.handle.SilentPacketHandler;
import org.rs2.janus.util.ClassTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class PacketMeta {

	/**
	 * 
	 */
	private static final Gson gson;

	/**
	 * 
	 */
	private static final Map<Integer, PacketHandler> packetHandlers = new HashMap<Integer, PacketHandler>();

	/**
	 * 
	 */
	private static final PacketHandler silentHandler = new SilentPacketHandler();

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Class.class, new ClassTypeAdapter());
		gson = builder.create();

		try {
			load();
		} catch (Exception e) {
			throw new RuntimeException("Error loading packet meta.", e);
		}
	}

	/**
	 * Loads the packet meta.
	 */
	protected static void load() throws Exception {
		Type type = new TypeToken<Map<Integer, Class>>() {
		}.getType();
		Map<Integer, Class> classMap = gson.fromJson(new FileReader("data/PacketMeta.json"), type);

		for (Entry<Integer, Class> classSet : classMap.entrySet())
			packetHandlers.put(classSet.getKey(), (PacketHandler) classSet.getValue().newInstance());
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
