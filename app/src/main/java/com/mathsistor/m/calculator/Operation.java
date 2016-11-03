package com.mathsistor.m.calculator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

public enum Operation {
    PI_CONSTANT(Math.PI),
    E_CONSTANT(Math.E),
    UNARY_NEGATIVE(a -> -a),
    SQUARE_ROOT(Math::sqrt),
    X_POWER_MINUS_1(a -> 1 / a),
    SQUARE(a -> a * a),
    CUBE(a -> a * a * a),
    CUBE_ROOT(a -> Math.pow(a, 1.0 / 3.0)),
    SIN(Math::sin),
    COS(Math::cos),
    TAN(Math::tan),
    EXP(Math::exp),
    TEN_POWER(a -> Math.pow(10, a)),
    LOG10(Math::log10),
    LN(Math::log),
    ADDITION((a, b) -> a + b),
    SUBTRACTION((a, b) -> a - b),
    MULTIPLICATION((a, b) -> a * b),
    DIVISION((a, b) -> a / b),
    N_POWER(Math::pow),
    N_ROOT((a, b) -> Math.pow(a, 1 / b)),
    RANDOM(Math::random),
    EQUALS;

    private DoubleSupplier supplier;
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

    Operation(DoubleSupplier supplier) {
        this.supplier = supplier;
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

    public DoubleSupplier getSupplier() {
        return supplier;
    }
}
