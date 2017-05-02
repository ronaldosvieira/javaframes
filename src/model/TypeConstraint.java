package model;

public class TypeConstraint<T> implements Constraint {
    private Class<T> type;

    public TypeConstraint(Class<T> type) {
        this.type = type;
    }

    @Override
    public boolean check(Object value) {
        return type.isAssignableFrom(value.getClass());
    }
}
