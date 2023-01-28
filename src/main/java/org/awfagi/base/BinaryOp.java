package org.awfagi.base;

import java.util.HashSet;
import java.util.Set;

public abstract class BinaryOp implements Expression, Computable {
    private final Expression left;
    private final Expression right;

    private final OperationType operationType;
    private final String symbol;

    public BinaryOp(Expression left, Expression right, OperationType operationType) {
        this.left = left;
        this.right = right;
        this.operationType = operationType;
        this.symbol = operationType.getSymbol();
    }

    @Override
    public boolean sameAs(Expression expression) {
        if (expression instanceof BinaryOp){
            BinaryOp casted = (BinaryOp) expression;
            return  left.sameAs(casted.getLeft()) &&
                    right.sameAs(casted.getRight()) &&
                    operationType == casted.getOperationType();
        }
        return false;
    }

    @Override
    public Set<String> getVariables() {
        HashSet<String> data = new HashSet<>();
        data.addAll(getLeft().getVariables());
        data.addAll(getRight().getVariables());
        return data;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "(" + left + ") " + symbol + " (" + right + ")";
    }
}
