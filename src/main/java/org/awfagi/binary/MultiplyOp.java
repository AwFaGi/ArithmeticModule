package org.awfagi.binary;

import org.awfagi.interfaces.Expression;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.Variable;

public class MultiplyOp extends BinaryOp {
    public MultiplyOp(Expression left, Expression right) {
        super(left, right, OperationType.MULTIPLY);
    }

    @Override
    public Expression derivative(Variable var) {
        Expression firstPart = new MultiplyOp(getLeft().derivative(var), getRight());
        Expression secondPart = new MultiplyOp(getLeft(), getRight().derivative(var));
        return new PlusOp(firstPart, secondPart);
    }

    @Override
    public double valueAt(double value) {
        if (getVariables().size() != 1){
            throw new BadVariablesNumberException(getVariables().size());
        }
        return getLeft().valueAt(value) * getRight().valueAt(value);
    }

    @Override
    public Expression valueAt(String name, double value) {
        return new MultiplyOp(getLeft().valueAt(name, value), getRight().valueAt(name, value));
    }

    @Override
    public Expression selfClone() {
        return new MultiplyOp(getLeft().selfClone(), getRight().selfClone());
    }
}
