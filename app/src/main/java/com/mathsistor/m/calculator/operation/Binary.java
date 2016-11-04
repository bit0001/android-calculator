package com.mathsistor.m.calculator.operation;

import java.util.function.DoubleBinaryOperator;

/**
 * Created by m on 11/3/2016.
 */

public class Binary extends Operation {
    private DoubleBinaryOperator binaryOperator;

    public Binary(DoubleBinaryOperator binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }
}
