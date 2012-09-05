/**
 * 
 */
package org.rs2.janus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.rs2.janus.net.NioServerNetwork;
import org.rs2.janus.net.archive.ArchiveChannelHandler;
import org.rs2.janus.net.archive.HttpPipelineFactory;
import org.rs2.janus.net.archive.JaggrabPipelineFactory;

/**
 * The Archive server is responsible for handling client connections requesting
 * archives, the Archives are then used by the client to check which files must
 * be updated. Archive requests can be sent via 2 protocols; the primary one
 * being the Http protocol (port 80), the second one being Jaggrab (port 43595)
 * is used as a backup in the case that Http fails.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class ArchiveServer {

	public static void main(String[] args) {
		new ArchiveServer().init();
	}

	/**
	 * The logger.
	 */
	private static final Logger log = Logger.getLogger(ArchiveServer.class);

	/**
	 * The executor responsible for both the Jaggrab and Http networks.
	 */
	private final ExecutorService executor = Executors.newFixedThreadPool(JanusProperties.getInt("ARCHIVE_THREADS"));

	/**
	 * The network responsible for connections via the Jaggrab protocol.
	 */
	private final NioServerNetwork jaggrabNetwork = new NioServerNetwork(executor);

	/**
	 * The network responsible for connections via the Http protocol.
	 */
	private final NioServerNetwork httpNetwork = new NioServerNetwork(executor);

	/**
	 * The handler responsible for servicing Archive requests.
	 */
	private final ArchiveChannelHandler archiveHandler = new ArchiveChannelHandler();

	/**
	 * The pipeline responsible for decoding Archive requests via the Jaggrab
	 * protocol.
	 */
	private final ChannelPipelineFactory jaggrabFactory = new JaggrabPipelineFactory(archiveHandler);

	/**
	 * The pipeline responsible for decoding Archive requests via the Http
	 * protocol.
	 */
	private final ChannelPipelineFactory httpFactory = new HttpPipelineFactory(archiveHandler);

	public void init() {
		log.info("Initiating Archive Server.");

		boolean httpFlag = initHttp();
		boolean jaggrabFlag = initJaggrab();

		if (!httpFlag && !jaggrabFlag)
			throw new RuntimeException("Error initiating both the Http and Jaggrab Networks!");

		log.info("Archive Server successfully initiated.");
	}

	/**
	 * Initiates the Http Network.
	 * 
	 * @return If the network was successfully initiated.
	 */
	private boolean initHttp() {
		log.info("Initiating Http Network.");

		try {
			log.info("Setting Http Network pipeline.");
			httpNetwork.setPipeline(httpFactory.getPipeline());
		} catch (Exception e) {
			log.error("Error setting Http Pipeline.", e);
			return false;
		}

		try {
			log.info("Binding Http Network.");
			httpNetwork.bind(JanusProperties.getInt("HTTP_SERVER_PORT"));
		} catch (Exception e) {
			log.error("Error binding Http Network.", e);
			return false;
		}

		return false;
	}

	/**
	 * Initiates the Jaggrab Network.
	 * 
	 * @return If the network was successfully initiated.
	 */
	public boolean initJaggrab() {
		log.info("Initiating Jaggrab Network.");

		try {
			log.info("Setting Jaggrab Network pipeline.");
			jaggrabNetwork.setPipeline(jaggrabFactory.getPipeline());
		} catch (Exception e) {
			log.error("Error setting Jaggrab Pipeline.", e);
			return false;
		}

		try {
			log.info("Binding Jaggrab Network.");
			jaggrabNetwork.bind(JanusProperties.getInt("JAGGRAB_SERVER_PORT"));
		} catch (Exception e) {
			log.error("Error binding Jaggrab Network.", e);
			return false;
		}

		return true;
	}
}
