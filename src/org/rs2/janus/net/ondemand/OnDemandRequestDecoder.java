/**
 * 
 */
package org.rs2.janus.net.ondemand;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.rs2.janus.net.request.OnDemandRequest;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class OnDemandRequestDecoder extends FrameDecoder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty
	 * .channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() >= 4) {
			int indexFile = buffer.readByte() + 1;
			int archiveFile = buffer.readShort();
			int priority = buffer.readByte();

			return new OnDemandRequest(priority, indexFile, archiveFile);
		}
		return null;
	}

}
