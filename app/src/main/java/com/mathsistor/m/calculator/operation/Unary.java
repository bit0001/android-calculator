package com.mathsistor.m.calculator.operation;

import java.util.function.DoubleUnaryOperator;


public class Unary extends Operation {
    private final DoubleUnaryOperator unaryOperator;

    public Unary(DoubleUnaryOperator unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return unaryOperator;
    }
}
