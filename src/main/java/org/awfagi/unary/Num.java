package org.awfagi.unary;

import org.awfagi.base.Expression;

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
    public boolean sameAs(Expression expression) {
        return expression instanceof Num && value == ((Num) expression).getValue();
    }

    @Override
    public Set<String> getVariables() {
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
}
