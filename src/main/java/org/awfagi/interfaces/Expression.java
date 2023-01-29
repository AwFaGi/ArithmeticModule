package org.awfagi.interfaces;

import org.awfagi.unary.Variable;

import java.util.Set;

public interface Expression extends Computable, Derivativable{
    Set<Variable> getVariables();
    Set<String> getVariablesAsString();
    Expression selfClone();
}
