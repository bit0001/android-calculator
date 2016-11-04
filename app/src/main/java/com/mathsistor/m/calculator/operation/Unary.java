package com.mathsistor.m.calculator.operation;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by m on 11/3/2016.
 */

public class Unary extends Operation {
    private final DoubleUnaryOperator unaryOperator;

    public Unary(DoubleUnaryOperator unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return unaryOperator;
    }
}
