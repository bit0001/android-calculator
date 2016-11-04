package com.mathsistor.m.calculator;

import com.mathsistor.m.calculator.operation.Binary;
import com.mathsistor.m.calculator.operation.Constant;
import com.mathsistor.m.calculator.operation.Equal;
import com.mathsistor.m.calculator.operation.Operation;
import com.mathsistor.m.calculator.operation.Random;
import com.mathsistor.m.calculator.operation.Unary;

public class OperationDescription {
    private String previousAppend;
    private String baseDescription;
    private String description;

    public OperationDescription() {
        description = "";
    }

    public String getDescription() {
        return description;
    }

    public void update(String symbol, Double accumulator, boolean isPartialResult) {
        Operation operation = Util.OPERATIONS.get(symbol);
        if (Constant.class.isInstance(operation)) {
            if (description.equals("\u03c0") || description.equals("e")) {
                description = symbol;
                return;
            }
            description += symbol;
        } else if (Unary.class.isInstance(operation)) {
            if (isPartialResult) {
                if (previousAppend != null) {
                    previousAppend = symbol + betweenParentheses(previousAppend);
                    description = baseDescription + previousAppend;
                } else {
                    baseDescription = description;
                    previousAppend = symbol + betweenParentheses(getAccumulatorString(accumulator));
                    description += previousAppend;
                }
            } else {
                description = symbol + betweenParentheses(description.equals("") ? getAccumulatorString(accumulator) : description);
            }
        } else if (Binary.class.isInstance(operation)) {
            if (isPartialResult) {
                if (previousAppend == null) {
                    description += getAccumulatorString(accumulator) + symbol;
                } else {
                    description += symbol;
                    previousAppend = null;
                }
            } else {
                description = (description.equals("") ? getAccumulatorString(accumulator) : description) + symbol;
            }
        } else if (Random.class.isInstance(operation)) {
        } else if (Equal.class.isInstance(operation)) {
            if (!isPartialResult) {
                return;
            }

            if (previousAppend != null) {
                previousAppend = null;
                return;
            }

            if (getAccumulatorString(accumulator).equals("e") || getAccumulatorString(accumulator).equals("\u03c0")) {
                return;
            }
            description += getAccumulatorString(accumulator);
        }
    }

    private String getAccumulatorString(Double accumulator) {
        if (accumulator == Math.PI) {
            return  "\u03c0";
        } else if (accumulator == Math.E) {
            return  "e";
        } else {
            return Util.formatNumber(accumulator);
        }
    }

    private String betweenParentheses(String string) {
        return "(" + string + ")";
    }

}
