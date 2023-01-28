package org.awfagi.binary;

import org.awfagi.base.BinaryOp;
import org.awfagi.base.Expression;
import org.awfagi.base.OperationType;
import org.awfagi.exceptions.BadOperationException;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.Num;
import org.awfagi.util.Nums;
import org.awfagi.unary.Variable;

public class PowerOp extends BinaryOp {
    public PowerOp(Expression left, Expression right) {
        super(left, right, OperationType.POWER);
    }

    @Override
    public double valueAt(double value) {
        if (getVariables().size() != 1){
            throw new BadVariablesNumberException(getVariables().size());
        }
        return Math.pow(getLeft().valueAt(value), getRight().valueAt(value));

    }

    @Override
    public Expression valueAt(String name, double value) {
        return new PowerOp(getLeft().valueAt(name, value), getRight().valueAt(name, value));
    }

    @Override
    public Expression derivative(Variable var) {
        if (!(getLeft() instanceof Num) && !(getRight() instanceof Num)){
            throw new BadOperationException("I can't take the derivative of expression ^ expression");
        }
        if (getLeft() instanceof Num){
            throw new BadOperationException("I can't work with logarithms");
        }

        Num power = (Num) getRight();

        if (power.sameAs(Nums.ONE)){
            return getLeft().derivative(var);
        }

        Num newPower = new Num(power.getValue() - 1);
        Expression expressionInPower = new PowerOp(getLeft(), newPower);
        Expression firstPart = new MultiplyOp(power, expressionInPower);
        return new MultiplyOp(firstPart, getLeft().derivative(var));

    }

    @Override
    public Expression selfClone() {
        return new PowerOp(getLeft().selfClone(), getRight().selfClone());
    }
}
