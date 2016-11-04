package com.mathsistor.m.calculator.operation;

import java.util.function.DoubleSupplier;

/**
 * Created by m on 11/3/2016.
 */

public class Constant extends Operation {
    private final double constant;

    public Constant(double constant) {
        this.constant = constant;
    }

    public double getConstant() {
        return constant;
    }
}
