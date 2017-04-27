package model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slot {
    private Object value;
    private Consumer<Object> if_added;
    private Supplier<Object> if_needed;

    public Slot() {}
    public Slot(Object value) {this.value = value;}

    public Object getValue() {
        // todo: consider default value
        if (value != null)
            return value;
        else if (if_needed != null)
            return if_needed.get();
        else
            return null;
    }

    public void setValue(Object value) {
        if (if_added != null) if_added.accept(value);

        this.value = value;
    }

    public void setIfAdded(Consumer<Object> if_added) {this.if_added = if_added;}
    public void setIfNeeded(Supplier<Object> if_needed) {this.if_needed = if_needed;}

    public boolean hasValue() {return this.value != null;}
}
