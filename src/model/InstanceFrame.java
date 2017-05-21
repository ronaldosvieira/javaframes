package model;

public class InstanceFrame extends Frame {
	public InstanceFrame(String name, GenericFrame parent) {
		super(name, parent);
	}

	public InstanceFrame(InstanceFrame frame) {super(frame);}

	public InstanceFrame clone() {return new InstanceFrame(this);}
}
