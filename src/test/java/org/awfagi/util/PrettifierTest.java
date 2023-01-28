package org.awfagi.util;

import org.awfagi.base.Expression;
import org.awfagi.binary.DivideOp;
import org.awfagi.binary.MultiplyOp;
import org.awfagi.binary.PlusOp;
import org.awfagi.binary.PowerOp;
import org.awfagi.unary.Num;
import org.awfagi.unary.Variable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrettifierTest {
    private static Expression semiExpression;
    private static Variable myVarB;
    private static Variable myVarX;
    private Prettifier prettifier;

    @BeforeAll
    public static void createExpression(){
        myVarB = new Variable('b');
        myVarX = new Variable("x_var");

        Expression myNum42 = new Num(42);
        Expression myNumMin3 = new Num(-3);

        semiExpression = new PlusOp(myNum42, myVarB);
        semiExpression = new MultiplyOp(semiExpression, myNumMin3);
        semiExpression = new PowerOp(semiExpression, myVarX);
        semiExpression = new DivideOp(myVarB, semiExpression);

        semiExpression = semiExpression.valueAt("x_var", 1);
    }

    @BeforeEach
    public void createPrettifier(){
        prettifier = new Prettifier();
    }

    @Test
    public void myVarBDerivativeTest(){
        String actual = prettifier.prettify(semiExpression.derivative(myVarB)).toString();
        String expected = "(((-3.0) * ((42.0) + (b))) + ((-1.0) * ((-3.0) * (b)))) /" +
                " (((-3.0) * ((42.0) + (b))) ^ (2.0))";

        assertEquals(expected, actual);
    }

    @Test
    public void myVarXDerivativeTest(){
        Expression actual = prettifier.prettify(semiExpression.derivative(myVarX));
        Expression expected = Nums.ZERO;

        assertTrue(expected.sameAs(actual));
    }
}
