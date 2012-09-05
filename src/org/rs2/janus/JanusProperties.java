/**
 * 
 */
package org.rs2.janus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Holds all of the properties for the Servers, this allows to change of Server
 * settings without recompiling.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class JanusProperties {

	/**
	 * Creates the {@link Properties} instance.
	 */
	private static Properties properties = new Properties();

	static {

		// Load all of the properties from the file.
		try {
			load("data/JanusProperties.ini");
		} catch (Exception e) {
			throw new RuntimeException("Error loading from properties file [/data/JanusProperties.ini]!", e);
		}
	}

	/**
	 * Reads properties from the given file.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void load(String file) throws Exception {
		properties.load(new FileInputStream(file));
	}

	/**
	 * Get's a property and parses it as a long.
	 * 
	 * @param property
	 *            The property to get.
	 * @return The property as a long.
	 */
	public static long getLong(String property) {
		return Long.parseLong(properties.getProperty(property));
	}

	/**
	 * Get's a property and parses it as a int.
	 * 
	 * @param property
	 *            The property to get.
	 * @return The property as a int.
	 */
	public static int getInt(String property) {
		return Integer.parseInt(properties.getProperty(property));
	}

	/**
	 * Get's a property and parses it as a double.
	 * 
	 * @param property
	 *            The property to get.
	 * @return The property as a double.
	 */
	public static double getDouble(String property) {
		return Double.parseDouble(properties.getProperty(property));
	}

	/**
	 * Get's a property and parses it as a string.
	 * 
	 * @param property
	 *            The property to get.
	 * @return The property as a string.
	 */
	public static String getString(String property) {
		return properties.getProperty(property);
	}
}
