package model;

import com.sun.istack.internal.NotNull;

public class TypeConstraint implements Constraint {
    private Class<?> type;

    public TypeConstraint(@NotNull Class<?> type) {
        if (type == null)
            throw new IllegalArgumentException("TypeConstraint with null class");

        this.type = type;
    }

    @Override
    public boolean check(Object value) {
        return type.isAssignableFrom(value.getClass());
    }
}
