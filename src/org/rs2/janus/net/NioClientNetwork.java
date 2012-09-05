/**
 * 
 */
package org.rs2.janus.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * A network wrapper for Netty, this network requests services.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class NioClientNetwork {

	/**
	 * The {@link ClientBootstrap} that this network will use.
	 */
	private final ClientBootstrap bootstrap;

	/**
	 * Creates a new instance using the specified executor.
	 * 
	 * @param executor
	 *            The {@link Executor} that the network will use for all events.
	 */
	public NioClientNetwork(Executor executor) {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(executor, executor));
	}

	/**
	 * Sets the networks {@link ChannelPipeline}, this should be used when each
	 * new connection should use the <b>same</b> {@link ChannelPipeline}.
	 * 
	 * @param factory
	 *            The factory that the {@link ServerBootstrap} will use.
	 */
	public void setPipeline(ChannelPipeline pipeline) {
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
		bootstrap.setPipelineFactory(factory);
	}

	/**
	 * Connects the network to address + port.
	 * 
	 * @param address
	 *            The address that the network will be connected to.
	 * @param port
	 *            The port that the network will be connected to.
	 */
	public void connect(String address, int port) {
		if (bootstrap.getPipelineFactory() == null && bootstrap.getPipeline() == null)
			throw new RuntimeException("You must first set a pipeline or pipeline factory before connecting.");

		bootstrap.connect(new InetSocketAddress(address, port));
	}
}
