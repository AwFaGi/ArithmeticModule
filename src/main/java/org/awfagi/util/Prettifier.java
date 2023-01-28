package org.awfagi.util;

import org.awfagi.base.BinaryOp;
import org.awfagi.base.Expression;
import org.awfagi.binary.*;
import org.awfagi.unary.Num;

public class Prettifier {
    public static Expression prettify(Expression expression){
        if (!(expression instanceof BinaryOp)){
            return expression;
        }
        BinaryOp casted = (BinaryOp) expression;
        Expression left = casted.getLeft();
        Expression right = casted.getRight();

        left = prettifyBinaryOp(left);
        right = prettifyBinaryOp(right);

        left = prettify(left);
        right = prettify(right);

        switch (casted.getOperationType()){
            case PLUS:
                return prettifyPlus(new PlusOp(left, right));
            case POWER:
                return prettifyPower(new PowerOp(left, right));
            case DIVIDE:
                return prettifyDivide(new DivideOp(left, right));
            case MULTIPLY:
                return prettifyMultiply(new MultiplyOp(left, right));
        }

        return expression;
    }

    private static Expression prettifyBinaryOp(Expression expression){
        if (expression instanceof BinaryOp){
            BinaryOp casted = (BinaryOp) expression;
            switch (casted.getOperationType()){
                case PLUS:
                    expression = prettifyPlus((PlusOp) expression);
                    break;
                case DIVIDE:
                    expression = prettifyDivide((DivideOp) expression);
                    break;
                case MULTIPLY:
                    expression = prettifyMultiply((MultiplyOp) expression);
                    break;
                case POWER:
                    expression = prettifyPower((PowerOp) expression);
                    break;
            }
        }
        return expression;
    }

    private static Expression prettifyPlus(PlusOp expression){

        if (expression.getLeft().sameAs(Nums.ZERO)){
            return expression.getRight();
        }
        if (expression.getRight().sameAs(Nums.ZERO)){
            return expression.getLeft();
        }

        if (Nums.isNum(expression.getLeft()) && Nums.isNum(expression.getRight())){
            double numberOne = ((Num) expression.getLeft()).getValue();
            double numberTwo = ((Num) expression.getLeft()).getValue();
            return new Num(numberOne + numberTwo);
        }

        return expression;
    }

    private static Expression prettifyMultiply(MultiplyOp expression){

        if (expression.getLeft().sameAs(Nums.ZERO) || expression.getRight().sameAs(Nums.ZERO)){
            return Nums.ZERO;
        }
        if (expression.getLeft().sameAs(Nums.ONE)){
            return expression.getRight();
        }
        if (expression.getRight().sameAs(Nums.ONE)){
            return expression.getLeft();
        }

        if (Nums.isNum(expression.getLeft()) && Nums.isNum(expression.getRight())){
            double numberOne = ((Num) expression.getLeft()).getValue();
            double numberTwo = ((Num) expression.getLeft()).getValue();
            return new Num(numberOne * numberTwo);
        }

        if (expression.getRight() instanceof Num && !(expression.getLeft() instanceof Num)){
            return new MultiplyOp(expression.getRight(), expression.getLeft());
        }

        return expression;
    }

    private static Expression prettifyDivide(DivideOp expression){

        if (expression.getLeft().sameAs(Nums.ZERO) && !expression.getRight().sameAs(Nums.ZERO)){
            return Nums.ZERO;
        }

        if (Nums.isNum(expression.getLeft()) && Nums.isNum(expression.getRight())){
            double numberOne = ((Num) expression.getLeft()).getValue();
            double numberTwo = ((Num) expression.getLeft()).getValue();
            return new Num(numberOne / numberTwo);
        }

        return expression;
    }

    private static Expression prettifyPower(PowerOp expression){

        if (expression.getLeft().sameAs(Nums.ZERO) && !expression.getRight().sameAs(Nums.ZERO)){
            return Nums.ZERO;
        }

        if (!expression.getLeft().sameAs(Nums.ZERO) && expression.getRight().sameAs(Nums.ZERO)){
            return Nums.ONE;
        }

        if (expression.getRight().sameAs(Nums.ONE)){
            return expression.getLeft();
        }

        if (Nums.isNum(expression.getLeft()) && Nums.isNum(expression.getRight())){
            double numberOne = ((Num) expression.getLeft()).getValue();
            double numberTwo = ((Num) expression.getLeft()).getValue();
            return new Num(Math.pow(numberOne, numberTwo));
        }

        return expression;
    }

}
