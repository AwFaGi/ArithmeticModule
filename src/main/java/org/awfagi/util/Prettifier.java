package org.awfagi.util;

import org.awfagi.binary.BinaryOp;
import org.awfagi.interfaces.Expression;
import org.awfagi.binary.*;
import org.awfagi.unary.Num;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Prettifier {

    private final Map<BinaryOp.OperationType, Function<Expression, Expression>> prettifyMap;
    private final BinaryOpFactory binaryOpFactory= new BinaryOpFactory();

    public Prettifier(){
        prettifyMap = new HashMap<>();
        prettifyMap.put(BinaryOp.OperationType.PLUS, expression -> prettifyPlus((PlusOp) expression));
        prettifyMap.put(BinaryOp.OperationType.MULTIPLY, expression -> prettifyMultiply((MultiplyOp) expression));
        prettifyMap.put(BinaryOp.OperationType.DIVIDE, expression -> prettifyDivide((DivideOp) expression));
        prettifyMap.put(BinaryOp.OperationType.POWER, expression -> prettifyPower((PowerOp) expression));

    }
    public Expression prettify(Expression expression){
        if (!(expression instanceof BinaryOp)){
            return expression;
        }
        BinaryOp casted = (BinaryOp) expression;
        Expression left = casted.getLeft();
        Expression right = casted.getRight();
        BinaryOp.OperationType operationType = casted.getOperationType();

        left = prettifyBinaryOp(left);
        right = prettifyBinaryOp(right);

        left = prettify(left);
        right = prettify(right);

        if (prettifyMap.containsKey(operationType)){
            return prettifyMap.get(operationType).apply(binaryOpFactory.createBinaryOp(operationType, left, right));
        }

        return expression;
    }

    private Expression prettifyBinaryOp(Expression expression){
        if (expression instanceof BinaryOp){
            BinaryOp casted = (BinaryOp) expression;
            BinaryOp.OperationType operationType = casted.getOperationType();
            if (prettifyMap.containsKey(operationType)){
                return prettifyMap.get(operationType).apply(expression);
            }
        }

        return expression;
    }

    private static Expression prettifyPlus(PlusOp expression){

        if (expression.getLeft().equals(Nums.ZERO)){
            return expression.getRight();
        }
        if (expression.getRight().equals(Nums.ZERO)){
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

        if (expression.getLeft().equals(Nums.ZERO) || expression.getRight().equals(Nums.ZERO)){
            return Nums.ZERO;
        }
        if (expression.getLeft().equals(Nums.ONE)){
            return expression.getRight();
        }
        if (expression.getRight().equals(Nums.ONE)){
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

        if (expression.getLeft().equals(Nums.ZERO) && !expression.getRight().equals(Nums.ZERO)){
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

        if (expression.getLeft().equals(Nums.ZERO) && !expression.getRight().equals(Nums.ZERO)){
            return Nums.ZERO;
        }

        if (!expression.getLeft().equals(Nums.ZERO) && expression.getRight().equals(Nums.ZERO)){
            return Nums.ONE;
        }

        if (expression.getRight().equals(Nums.ONE)){
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
