package model;

public class Slot {
    private Object value;

    public Slot() {}
    public Slot(Object value) {this.value = value;}

    public Object getValue() {
        // todo: use value or if_needed or default
        return this.value;
    }
    public void setValue(Object value) {this.value = value;}

    public boolean hasValue() {return this.value != null;}
}
