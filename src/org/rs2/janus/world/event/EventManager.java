/**
 * 
 */
package org.rs2.janus.world.event;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class EventManager {

	/**
	 * 
	 */
	private final LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>();

	/**
	 * 
	 */
	public void pulse() {
		Iterator<Event> it = eventQueue.iterator();

		while (it.hasNext()) {
			Event event = it.next();

			event.pulse();

			if (!event.isRunning())
				it.remove();
		}
	}

	/**
	 * 
	 */
	public void removeEvent(Event event) {
		eventQueue.remove(event);
	}

	/**
	 * 
	 */
	public void addEvent(Event event) {
		eventQueue.add(event);
	}

}
