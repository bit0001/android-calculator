package com.mathsistor.m.calculator.operation;

public class Constant extends Operation {
    private final double constant;

    public Constant(double constant) {
        this.constant = constant;
    }

    public double getConstant() {
        return constant;
    }
}
