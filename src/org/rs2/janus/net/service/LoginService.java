/**
 * 
 */
package org.rs2.janus.net.service;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.rs2.janus.net.request.LoginRequest;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LoginService implements Service<LoginRequest> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rs2.janus.net.service.Service#serviceRequest(org.jboss.netty.channel
	 * .ChannelHandlerContext, org.rs2.janus.net.request.Request)
	 */
	@Override
	public void serviceRequest(ChannelHandlerContext ctx, LoginRequest request) throws Exception {
		// TODO Auto-generated method stub

	}

}
