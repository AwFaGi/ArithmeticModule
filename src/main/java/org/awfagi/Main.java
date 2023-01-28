package org.awfagi;

import org.awfagi.base.Expression;
import org.awfagi.binary.*;
import org.awfagi.exceptions.BadOperationException;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.*;
import org.awfagi.util.Prettifier;

public class Main {

    private static void print(String message){
        System.out.println("\n--- " + message);
    }

    public static void main(String[] args) {

        Expression expression;
        Prettifier prettifier = new Prettifier();

        Variable myVarB = new Variable('b');
        Variable myVarX = new Variable("x_var");
        Expression myNum42 = new Num(42);
        Expression myNumMin3 = new Num(-3);

        expression = new PlusOp(myNum42, myVarB);
        expression = new MultiplyOp(expression, myNumMin3);
        expression = new PowerOp(expression, myVarX);
        expression = new DivideOp(myVarB, expression);

        print("Expression:");
        System.out.println(expression);

        print("Clone:");
        System.out.println(expression.selfClone());

        print("Variables:");
        System.out.println(expression.getVariables());

        print("Value at b=0:");
        try {
            System.out.println(expression.valueAt(0));
        } catch (BadVariablesNumberException e){
            System.out.println(e.getMessage());
        }

        print("Replace x_var with 1:");
        Expression semiExpression = expression.valueAt("x_var", 1);
        System.out.println(semiExpression);

        print("Real value at b=0:");
        System.out.println(semiExpression.valueAt(0));

        print("Real value at b=1:");
        System.out.printf("%f\n", semiExpression.valueAt(1));

        print("Derivative:");
        try {
            System.out.println(expression.derivative(myVarB));
        } catch (BadOperationException e){
            System.out.println(e.getMessage());
        }

        print("Derivative (with x_var replaced with 1):");
        System.out.println(semiExpression.derivative(myVarB));

        print("Prettified derivative:");
        System.out.println(prettifier.prettify(semiExpression.derivative(myVarB)));

        print("Derivative by x_var:");
        System.out.println(semiExpression.derivative(myVarX));

        print("Prettified derivative by x_var:");
        System.out.println(prettifier.prettify(semiExpression.derivative(myVarX)));
    }
}