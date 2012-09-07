/**
 * 
 */
package org.rs2.janus.net.service;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.net.request.Request;

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
