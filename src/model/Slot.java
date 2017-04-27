package model;

import java.util.function.Consumer;

public class Slot {
    private Object value;
    private Consumer<Object> if_added;

    public Slot() {}
    public Slot(Object value) {this.value = value;}

    public Object getValue() {
        // todo: use value or if_needed or default
        return this.value;
    }

    public void setValue(Object value) {
        if (if_added != null) if_added.accept(value);

        this.value = value;
    }

    public void setIfAdded(Consumer<Object> if_added) {this.if_added = if_added;}

    public boolean hasValue() {return this.value != null;}
}
