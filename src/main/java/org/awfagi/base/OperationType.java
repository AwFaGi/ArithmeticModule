package org.awfagi.base;

public enum OperationType {
    DIVIDE("/"),
    MULTIPLY("*"),
    PLUS("+"),
    POWER("^");
    private final String symbol;

    public String getSymbol() {
        return symbol;
    }
    OperationType(String symbol){
        this.symbol = symbol;
    }
}
