/**
 * 
 */
package org.rs2.janus.world.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.rs2.janus.world.net.packet.PacketConstants.PacketHeader;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class Packet {

	/**
	 * 
	 */
	private final int opcode;

	/**
	 * 
	 */
	private final PacketHeader header;

	/**
	 * 
	 */
	private final ChannelBuffer payload;

	/**
	 * 
	 */
	public Packet(int opcode, PacketHeader header, ChannelBuffer payload) {
		this.opcode = opcode;
		this.header = header;
		this.payload = payload;
	}

	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @return the header
	 */
	public PacketHeader getHeader() {
		return header;
	}

	/**
	 * @return the payload
	 */
	public ChannelBuffer getPayload() {
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Packet [opcode=" + opcode + ", header=" + header + ", payload=" + payload + "]";
	}
}
