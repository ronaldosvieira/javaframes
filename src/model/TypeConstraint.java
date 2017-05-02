package model;

public class TypeConstraint implements Constraint {
    private Class<?> type;

    public TypeConstraint(Class<?> type) {
        this.type = type;
    }

    @Override
    public boolean check(Object value) {
        return type.isAssignableFrom(value.getClass());
    }
}
