package model;

import java.util.HashMap;

public class InstanceFrame extends Frame {
    private GenericFrame instanceOf;
	private HashMap<String, Property> slots;

	public InstanceFrame() {
		this.slots = new HashMap<>();
	}
	
	public InstanceFrame(GenericFrame instanceOf) {
		this();

		this.instanceOf = instanceOf;
	}

    @Override
	public Frame getParent() {
	    return this.instanceOf;
    }

    @Override
    public Object get(String slot) {
        // todo: implement

        return null;
    }

    @Override
    public <T> void put(String slot, T filler) {
        // todo: implement
    }


}
