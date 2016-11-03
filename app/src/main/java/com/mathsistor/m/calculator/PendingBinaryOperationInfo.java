package com.mathsistor.m.calculator;

import java.util.function.DoubleBinaryOperator;

public class PendingBinaryOperationInfo {
    private DoubleBinaryOperator binaryOperator;
    private double firstOperand;

    public PendingBinaryOperationInfo(DoubleBinaryOperator binaryOperator, double firstOperand) {
        this.binaryOperator = binaryOperator;
        this.firstOperand = firstOperand;
    }

    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }

    public double getFirstOperand() {
        return firstOperand;
    }
}
