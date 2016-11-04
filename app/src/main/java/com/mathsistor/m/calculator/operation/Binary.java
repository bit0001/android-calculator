package com.mathsistor.m.calculator.operation;

import java.util.function.DoubleBinaryOperator;


public class Binary extends Operation {
    private DoubleBinaryOperator binaryOperator;

    public Binary(DoubleBinaryOperator binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }
}
