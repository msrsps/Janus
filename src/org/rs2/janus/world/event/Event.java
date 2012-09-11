/**
 * 
 */
package org.rs2.janus.world.event;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public abstract class Event {

	/**
	 * 
	 */
	private boolean isRunning = true;

	/**
	 * 
	 */
	private final int absoluteDelay;

	/**
	 * 
	 */
	private int timer = 0;

	/**
	 * 
	 */
	public Event(boolean immediate, int delay) {
		if (immediate)
			execute();

		this.absoluteDelay = delay;
		this.timer = delay;
	}

	/**
	 * 
	 */
	public abstract void execute();

	/**
	 * 
	 */
	public void pulse() {
		if (timer-- == 0)
			execute();
	}

	/**
	 * 
	 */
	public void stop() {
		isRunning = false;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

}
