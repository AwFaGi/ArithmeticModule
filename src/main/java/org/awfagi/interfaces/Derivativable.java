package org.awfagi.interfaces;

import org.awfagi.unary.Variable;

public interface Derivativable {
    Expression derivative(Variable var);
}
