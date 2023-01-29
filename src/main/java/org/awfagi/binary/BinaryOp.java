package org.awfagi.binary;

import org.awfagi.interfaces.Computable;
import org.awfagi.interfaces.Expression;
import org.awfagi.unary.Variable;

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
    public Set<Variable> getVariables() {
        HashSet<Variable> data = new HashSet<>();
        data.addAll(getLeft().getVariables());
        data.addAll(getRight().getVariables());
        return data;
    }

    @Override
    public Set<String> getVariablesAsString() {
        HashSet<String> data = new HashSet<>();
        data.addAll(getLeft().getVariablesAsString());
        data.addAll(getRight().getVariablesAsString());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOp binaryOp = (BinaryOp) o;

        if (!left.equals(binaryOp.left)) return false;
        if (!right.equals(binaryOp.right)) return false;
        return operationType == binaryOp.operationType;
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        result = 31 * result + operationType.hashCode();
        return result;
    }

    public enum OperationType {
        DIVIDE("/"),
        MULTIPLY("*"),
        PLUS("+"),
        POWER("^");
        private final String symbol;

        public String getSymbol() {
            return symbol;
        }
        OperationType(String symbol){
            this.symbol = symbol;
        }
    }
}
