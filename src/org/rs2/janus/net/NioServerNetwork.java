/**
 * 
 */
package org.rs2.janus.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * A network wrapper for Netty, this network provides services.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class NioServerNetwork {

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(NioServerNetwork.class);

	/**
	 * The {@link ServerBootstrap} that this network will use.
	 */
	private final ServerBootstrap bootstrap;

	/**
	 * Creates a new instance using the specified executor.
	 * 
	 * @param executor
	 *            The {@link Executor} that the network will use for all events.
	 */
	public NioServerNetwork(Executor executor) {
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(executor, executor));
	}

	/**
	 * Sets the networks {@link ChannelPipeline}, this should be used when each
	 * new connection should use the <b>same</b> {@link ChannelPipeline}.
	 * 
	 * @param factory
	 *            The factory that the {@link ServerBootstrap} will use.
	 */
	public void setPipeline(ChannelPipeline pipeline) {
		log.info("Network pipeline set.");
		bootstrap.setPipeline(pipeline);
	}

	/**
	 * Sets the networks {@link ChannelPipelineFactory}, this should be used
	 * when each new connection requires a <b>different</b>
	 * {@link ChannelPipeline}.
	 * 
	 * @param factory
	 *            The factory that the {@link ServerBootstrap} will use.
	 */
	public void setPipelineFactory(ChannelPipelineFactory factory) {
		log.info("Network pipeline factory set.");
		bootstrap.setPipelineFactory(factory);
	}

	/**
	 * Binds the network to port.
	 * 
	 * @param port
	 *            The port that the network will be binded to.
	 */
	public void bind(int port) throws Exception {
		log.info("Attempting to bind network to port " + port + "...");

		if (bootstrap.getPipelineFactory() == null && bootstrap.getPipeline() == null)
			throw new Exception("You must first set a pipeline or pipeline factory before binding!");

		try {
			bootstrap.bind(new InetSocketAddress(port));
		} catch (Exception e) {
			throw new Exception("Error binding network to port, is port already in use?", e);
		}

		log.info("... Network successfully binded to port " + port + "!");
	}

	/**
	 * @return the bootstrap
	 */
	public ServerBootstrap getBootstrap() {
		return bootstrap;
	}
}
