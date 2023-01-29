package org.awfagi.interfaces;

import org.awfagi.binary.DivideOp;
import org.awfagi.binary.MultiplyOp;
import org.awfagi.binary.PlusOp;
import org.awfagi.binary.PowerOp;
import org.awfagi.exceptions.BadOperationException;
import org.awfagi.exceptions.BadVariablesNumberException;
import org.awfagi.unary.Num;
import org.awfagi.unary.Variable;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class ExpressionTest {

    private static Expression expression;
    private static Expression semiExpression;
    private static Variable myVarB;
    private static Variable myVarX;

    @BeforeAll
    public static void createExpression(){
        myVarB = new Variable('b');
        myVarX = new Variable("x_var");

        Expression myNum42 = new Num(42);
        Expression myNumMin3 = new Num(-3);

        expression = new PlusOp(myNum42, myVarB);
        expression = new MultiplyOp(expression, myNumMin3);
        expression = new PowerOp(expression, myVarX);
        expression = new DivideOp(myVarB, expression);

        semiExpression = expression.valueAt("x_var", 1);
    }

    @Test
    public void expressionToStringTest(){
        String actual = expression.toString();
        String expected = "(b) / ((((42.0) + (b)) * (-3.0)) ^ (x_var))";

        assertEquals(expected, actual);
    }

    @Test
    public void cloneTest(){
        Expression cloned = expression.selfClone();

        assertEquals(cloned, expression);
    }

    @Test
    public void variablesTest(){
        Set<Variable> variables = expression.getVariables();

        assertEquals(2, variables.size());
        assertTrue(variables.contains(myVarB));
        assertTrue(variables.contains(myVarX));
    }

    @Test
    public void expressionValueTest(){
        Throwable throwable = assertThrows(BadVariablesNumberException.class,
                () -> expression.valueAt(0)
        );
        assertEquals("Expression has 2 variables, but must have only 1", throwable.getMessage());
    }

    @Test
    public void expressionDerivativeTest(){
        Throwable throwable = assertThrows(BadOperationException.class,
                () -> expression.derivative(myVarB)
        );
        assertEquals("I can't take the derivative of expression ^ expression", throwable.getMessage());
    }

    @Test
    public void semiExpressionToStringTest(){
        String actual = semiExpression.toString();
        String expected = "(b) / ((((42.0) + (b)) * (-3.0)) ^ (1.0))";

        assertEquals(expected, actual);
    }

    @Test
    public void semiExpressionValueTest(){
        double actual = semiExpression.valueAt(0);
        double expected = -0D;

        assertEquals(expected, actual);

        actual = semiExpression.valueAt(1);
        expected = 1D / (43 * (-3));

        assertEquals(expected, actual);
    }

    @Test
    public void semiExpressionDerivativeTest(){
        String actual = semiExpression.derivative(myVarB).toString();
        String expected =
                "(" +
                "((1.0) * ((((42.0) + (b)) * (-3.0)) ^ (1.0))) +" +
                " ((-1.0) * ((b) * ((((0.0) + (1.0)) * (-3.0)) + (((42.0) + (b)) * (0.0)))))" +
                ") / (((((42.0) + (b)) * (-3.0)) ^ (1.0)) ^ (2.0))";

        assertEquals(expected, actual);

        actual = semiExpression.derivative(myVarX).toString();
        expected =
                "(" +
                "((0.0) * ((((42.0) + (b)) * (-3.0)) ^ (1.0))) +" +
                " ((-1.0) * ((b) * ((((0.0) + (0.0)) * (-3.0)) + (((42.0) + (b)) * (0.0)))))" +
                ") / (((((42.0) + (b)) * (-3.0)) ^ (1.0)) ^ (2.0))";

        assertEquals(expected, actual);
    }


}
