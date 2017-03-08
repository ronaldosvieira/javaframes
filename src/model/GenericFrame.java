package model;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericFrame extends Frame {
    private GenericFrame isA;
    private HashMap<String, Property> slots;
    private HashMap<String, Consumer<Frame>> ifAdded;
    private HashMap<String, Supplier> ifNeeded;

    public GenericFrame() {
        this.slots = new HashMap<>();
        this.ifAdded = new HashMap<>();
        this.ifNeeded = new HashMap<>();
	}
	
	public GenericFrame(GenericFrame isA) {
		this();

        this.isA = isA;
	}

    @Override
    public Frame getParent() {
        return this.isA;
    }

    @Override
	public Object get(String slot) {
        if (this.ifNeeded.containsKey(slot)) {
            return this.ifNeeded.get(slot).get();
        }

        if (this.slots.containsKey(slot)) {
            return this.slots.get(slot).get();
        }

        if (this.getParent() != null) {
            return this.getParent().get(slot);
        } else {
            throw new NoSuchElementException("Slot '" + slot + "' not found");
        }
    }

    @Override
    public <T> void put(String slot, T filler) {
        this.slots.put(slot, new Property<>(filler));

        if (this.ifAdded.containsKey(slot)) {
            this.ifAdded.get(slot).accept(this);
        }
    }
}
