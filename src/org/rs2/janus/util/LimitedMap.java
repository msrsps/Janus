/**
 * 
 */
package org.rs2.janus.util;

import java.util.HashMap;

/**
 * A {@link HashMap} that is limited in size.
 * 
 * @author Michael Schmidt <H3llKing> <msrsps@hotmail.com>
 * 
 */
public class LimitedMap<K, V> extends HashMap<K, V> {

	/**
	 * The capacity of the map.
	 */
	private final int capacity;

	/**
	 * New instance.
	 * 
	 * @param capacity
	 *            The capacity of the map.
	 */
	public LimitedMap(int capacity) {
		super(capacity);
		this.capacity = capacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		if (size() < capacity)
			return super.put(key, value);
		return null;
	}
}
