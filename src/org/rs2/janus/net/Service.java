/**
 * 
 */
package org.rs2.janus.net;

import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public interface Service<R extends Request> {

	/**
	 * 
	 */
	public void serviceRequest(ChannelHandlerContext ctx, R request) throws Exception;

}
