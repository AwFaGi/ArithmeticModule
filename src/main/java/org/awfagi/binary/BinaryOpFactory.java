package org.awfagi.binary;

import org.awfagi.base.BinaryOp;
import org.awfagi.base.Expression;
import org.awfagi.base.OperationType;

public class BinaryOpFactory {
    public BinaryOp createBinaryOp(OperationType type, Expression left, Expression right){
        switch (type){
            case PLUS:
                return new PlusOp(left, right);
            case POWER:
                return new PowerOp(left, right);
            case DIVIDE:
                return new DivideOp(left, right);
            case MULTIPLY:
                return new MultiplyOp(left, right);
            default:
                return null;
        }
    }
}
