package org.awfagi.interfaces;

public interface Computable {
    double valueAt(double value);
    Expression valueAt(String name, double value);
}
