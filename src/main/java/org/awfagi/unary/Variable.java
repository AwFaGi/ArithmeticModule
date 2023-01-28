package org.awfagi.unary;

import org.awfagi.base.Expression;

import java.util.HashSet;
import java.util.Set;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(char name) {
        this.name = String.valueOf(name);
    }

    @Override
    public Expression derivative(Variable variable) {
        if (getName().equals(variable.getName())){
            return new Num(1);
        }
        return new Num(0);
    }

    @Override
    public double valueAt(double value) {
        return value;
    }

    @Override
    public Expression valueAt(String name, double value) {
        if (getName().equals(name)){
            return new Num(value);
        }
        return selfClone();
    }

    @Override
    public boolean sameAs(Expression expression) {
        return expression instanceof Variable && name.equals(((Variable) expression).getName());
    }

    @Override
    public Set<String> getVariables() {
        HashSet<String> data = new HashSet<>();
        data.add(getName());
        return data;
    }

    @Override
    public Expression selfClone() {
        return new Variable(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
