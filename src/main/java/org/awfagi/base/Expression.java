package org.awfagi.base;

import org.awfagi.unary.Variable;

import java.util.Set;

public interface Expression extends Computable{
    Expression selfClone();
    Expression derivative(Variable var);
    boolean sameAs(Expression expression);
    Set<String> getVariables();
    String toString();
}
