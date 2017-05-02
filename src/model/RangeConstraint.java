package model;

import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Contract;

import java.util.function.BiPredicate;

public class RangeConstraint<T> implements Constraint {
    private T lo, hi;
    private BiPredicate[] operators = new BiPredicate[2];

    private static final BiPredicate<Comparable, Comparable> lessThan =
            (c1, c2) -> c1.compareTo(c2) < 0;
    private static final BiPredicate<Comparable, Comparable> lessThanOrEqual =
            (c1, c2) -> c1.compareTo(c2) <= 0;
    private static final BiPredicate<Comparable, Comparable> greaterThan =
            (c1, c2) -> c1.compareTo(c2) > 0;
    private static final BiPredicate<Comparable, Comparable> greaterThanOrEqual =
            (c1, c2) -> c1.compareTo(c2) >= 0;

    @Contract("null, null -> fail")
    public RangeConstraint(T lo, T hi) {
        if (lo == null && hi == null)
            throw new IllegalArgumentException("Lo and hi can't both be null");

        this.lo = lo;
        this.hi = hi;
    }

    @Contract("_, _, null -> fail")
    public RangeConstraint(T lo, T hi, @NotNull String inclusivity) {
        this(lo, hi);

        if (inclusivity == null || inclusivity.length() != 2)
            throw new IllegalArgumentException("Invalid inclusivity string");

        char incl = inclusivity.charAt(0);

        if (incl == '[') operators[0] = lessThanOrEqual;
        else if (incl == '(' || incl == ']') operators[0] = lessThan;
        else throw new IllegalArgumentException("Invalid inclusivity string");

        incl = inclusivity.charAt(1);

        if (incl == ']') operators[1] = greaterThanOrEqual;
        else if (incl == ')' || incl == '[') operators[1] = greaterThan;
        else throw new IllegalArgumentException("Invalid inclusivity string");
    }

    @Override
    public boolean check(Object value) {
        if (!(value instanceof Comparable)) return false;

        return operators[0].test(value, lo) && operators[1].test(value, hi);
    }
}
