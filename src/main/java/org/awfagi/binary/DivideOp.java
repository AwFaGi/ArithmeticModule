package org.awfagi.binary;

import org.awfagi.base.BinaryOp;
import org.awfagi.base.Expression;
import org.awfagi.base.OperationType;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.Num;
import org.awfagi.unary.Variable;

public class DivideOp extends BinaryOp {
    public DivideOp(Expression left, Expression right) {
        super(left, right, OperationType.DIVIDE);
    }

    @Override
    public Expression derivative(Variable var) {
        Expression upperLeftPart = new MultiplyOp(getLeft().derivative(var), getRight());
        Expression preUpperRightPart = new MultiplyOp(getLeft(), getRight().derivative(var));
        Expression upperRightPart = new MultiplyOp(new Num(-1), preUpperRightPart);
        Expression upperPart = new PlusOp(upperLeftPart, upperRightPart);
        Expression bottomPart = new PowerOp(getRight(), new Num(2));

        return new DivideOp(upperPart, bottomPart);
    }

    @Override
    public double valueAt(double value) {
        if (getVariables().size() != 1){
            throw new BadVariablesNumberException(getVariables().size());
        }
        return getLeft().valueAt(value) / getRight().valueAt(value);
    }

    @Override
    public Expression valueAt(String name, double value) {
        return new DivideOp(getLeft().valueAt(name, value), getRight().valueAt(name, value));
    }

    @Override
    public Expression selfClone() {
        return new DivideOp(getLeft().selfClone(), getRight().selfClone());
    }
}
