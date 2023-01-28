package org.awfagi.util;

import org.awfagi.base.Expression;
import org.awfagi.unary.Num;

public class Nums {
    public static final Num ZERO = new Num(0);
    public static final Num ONE = new Num(1);

    public static boolean isNum(Expression expression){
        return expression instanceof Num;
    }
}
