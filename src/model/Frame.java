package model;

public abstract class Frame {
	abstract public Frame getParent();
	abstract public Object get(String slot);
	abstract public <T> void put(String slot, T filler);
}
