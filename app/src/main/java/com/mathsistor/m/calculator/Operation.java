package com.mathsistor.m.calculator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by m on 10/29/2016.
 */

public enum Operation {
    PI_CONSTANT(Math.PI),
    E_CONSTANT(Math.E),
    SQUARE_ROOT(Math::sqrt),
    SQUARE(a -> a * a),
    SIN(Math::sin),
    COS(Math::cos),
    TAN(Math::tan),
    EXP(Math::exp),
    TEN_POWER(a -> Math.pow(10, a)),
    ADDITION((a, b) -> a + b),
    SUBTRACTION((a, b) -> a - b),
    MULTIPLICATION((a, b) -> a * b),
    DIVISION((a, b) -> a / b),
    EQUALS;

    private double constant;
    private DoubleUnaryOperator unaryOperator;
    private DoubleBinaryOperator binaryOperator;

    Operation(double constant) {
        this.constant = constant;
    }

    Operation(DoubleUnaryOperator unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    Operation(DoubleBinaryOperator binaryOperator) {
        this.binaryOperator = binaryOperator;
    }


    Operation() {

    }

    public double getConstant() {
        return constant;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return unaryOperator;
    }

    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }
}
