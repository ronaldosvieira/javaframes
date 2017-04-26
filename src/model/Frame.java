package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class Frame {
    private String name;
	protected GenericFrame parent;
	private Map<String, Slot> slots;
	
	public Frame(String name) {
	    this.name = name;
	    this.slots = new HashMap<>();
	}
	
	public Frame(String name, GenericFrame parent) {
        this(name);
        this.parent = parent;
    }

    public String getName() {return this.name;}
	public GenericFrame parent() {return this.parent;}
    public void setName(String name) {this.name = name;}

    public boolean contains(String key) {
	    return slots.containsKey(key);
    }
    
    protected Slot find(String key) throws NoSuchElementException {
	    if (this.contains(key))
	        return slots.get(key);
	    else if (parent != null && parent.contains(key))
	        return parent.find(key);
	    else
	        throw new NoSuchElementException("Slot '" + key
                    + "' not found on frame " + getName());
    }

	public Object get(String key) throws NoSuchElementException {
	    return this.find(key).getValue();
	}

	public <T> T get(String key, Class<T> type) throws ClassCastException {
        Object value = this.get(key);

	    try {
            return type.cast(value);
        } catch (ClassCastException e) {
            throw new ClassCastException("Slot '" + key + "' is of type "
                    + value.getClass().getSimpleName() + ". "
                    + type.getSimpleName() + " given.");
        }
	}
	
	public <T> void add(String key, T value) {
	    try {
	        Slot slot = this.find(key);
	        slot.setValue(value);

	        slots.put(key, slot);
        } catch (NoSuchElementException e) {
            slots.put(key, new Slot(value));
        }
	}
}
