package org.awfagi.exceptions;

public class BadVariablesNumberException extends RuntimeException{
    public BadVariablesNumberException(int number) {
        super("Expression has " + number + " variables, " +
                "but must have only 1");
    }
}
