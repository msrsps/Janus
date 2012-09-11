/**
 * 
 */
package org.rs2.janus.world.net;

import org.jboss.netty.channel.Channel;
import org.rs2.janus.world.WorldContext;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public interface Service<R extends Request> {

	/**
	 * @param context
	 * @param message
	 */
	public void messageReceived(WorldContext context, Channel channel, R request);

}
