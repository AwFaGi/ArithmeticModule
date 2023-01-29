package org.awfagi.unary;

import org.awfagi.interfaces.Expression;

import java.util.HashSet;
import java.util.Set;

public class Num implements Expression {
    public Num(double value) {
        this.value = value;
    }
    private final double value;

    @Override
    public Expression derivative(Variable variable) {
        return new Num(0);
    }

    @Override
    public double valueAt(double value) {
        return getValue();
    }

    @Override
    public Expression valueAt(String name, double value) {
        return selfClone();
    }

    @Override
    public Set<Variable> getVariables() {
        return new HashSet<>();
    }

    public Set<String> getVariablesAsString() {
        return new HashSet<>();
    }

    @Override
    public Expression selfClone() {
        return new Num(value);
    }

    public double getValue() {
        return value;
    }



    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Num num = (Num) o;

        return Double.compare(num.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
