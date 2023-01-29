package org.awfagi.unary;

import org.awfagi.interfaces.Expression;

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
    public Set<Variable> getVariables() {
        HashSet<Variable> data = new HashSet<>();
        data.add(this);
        return data;
    }

    @Override
    public Set<String> getVariablesAsString() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
