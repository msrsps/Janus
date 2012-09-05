/**
 * 
 */
package org.rs2.janus.net.archive;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 * Decodes a {@link HttpRequest} to a {@link ArchiveRequest}.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class HttpToArchiveDecoder extends OneToOneDecoder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneDecoder#decode(org.jboss
	 * .netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * java.lang.Object)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			String request = ((HttpRequest) msg).getUri();
			return new ArchiveRequest(request.substring(1));
		}
		return null;
	}

}
