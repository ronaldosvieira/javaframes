package model;

public class GenericFrame extends Frame {
	public GenericFrame(String name) {
		super(name);
	}
	
	public GenericFrame(String name, GenericFrame parent) {
		super(name, parent);
	}

}
