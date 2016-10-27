package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class Frame {
	private Map<String, Property<? extends Object>> properties;
	private Map<String, Runnable> ifAdded;
	private Map<String, Runnable> ifNeeded;
	
	public Frame() {
		this.properties = new HashMap<>();
		this.ifAdded = new HashMap<>();
		this.ifNeeded = new HashMap<>();
	}

	public Object get(String key) {
		if (this.properties.containsKey(key)) {
			if (this.ifNeeded.containsKey(key)) {
				this.ifNeeded.get(key).run();
			}
			
			return this.properties.get(key).get();
		} else {
			throw new NoSuchElementException("Property not found");
		}
	}
	
	public <T> T get(String key, Class<T> type) {
		if (this.properties.containsKey(key)) {
			if (this.ifNeeded.containsKey(key)) {
				this.ifNeeded.get(key).run();
			}
			
			try {
				return type.cast(this.properties.get(key).get());
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Get property with wrong type");
			}
		} else {
			throw new NoSuchElementException("Property not found");
		}
	}
	
	public <T> void add(String key, T value) {
		this.properties.put(key, new Property<Object>(value));
		
		if (this.ifAdded.containsKey(key)) {
			this.ifAdded.get(key).run();
		}
	}
}
