package org.awfagi.binary;

import org.awfagi.base.BinaryOp;
import org.awfagi.base.Expression;
import org.awfagi.base.OperationType;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.Variable;

public class PlusOp extends BinaryOp {
    public PlusOp(Expression left, Expression right) {
        super(left, right, OperationType.PLUS);
    }

    @Override
    public Expression derivative(Variable var) {
        return new PlusOp(getLeft().derivative(var), getRight().derivative(var));
    }

    @Override
    public double valueAt(double value) {
        if (getVariables().size() != 1){
            throw new BadVariablesNumberException(getVariables().size());
        }
        return getLeft().valueAt(value) + getRight().valueAt(value);
    }

    @Override
    public Expression valueAt(String name, double value) {
        return new PlusOp(getLeft().valueAt(name, value), getRight().valueAt(name, value));
    }

    @Override
    public Expression selfClone() {
        return new PlusOp(getLeft().selfClone(), getRight().selfClone());
    }
}
