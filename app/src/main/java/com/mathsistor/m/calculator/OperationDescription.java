package com.mathsistor.m.calculator;

import com.mathsistor.m.calculator.operation.Binary;
import com.mathsistor.m.calculator.operation.Constant;
import com.mathsistor.m.calculator.operation.Equal;
import com.mathsistor.m.calculator.operation.Operation;
import com.mathsistor.m.calculator.operation.Random;
import com.mathsistor.m.calculator.operation.Unary;
import com.mathsistor.m.calculator.util.Maps;

import static com.mathsistor.m.calculator.util.Formatter.betweenParentheses;
import static com.mathsistor.m.calculator.util.Formatter.getNumberString;

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
        Operation operation = Maps.OPERATIONS.get(symbol);
        if (Constant.class.isInstance(operation)) {
        } else if (Unary.class.isInstance(operation)) {
            if (isPartialResult) {
                if (previousAppend != null) {
                    previousAppend = symbol + betweenParentheses(previousAppend);
                    description = baseDescription + previousAppend;
                } else {
                    baseDescription = description;
                    previousAppend = symbol + betweenParentheses(getNumberString(accumulator));
                    description += previousAppend;
                }
            } else {
                description = symbol + betweenParentheses(description.equals("") ? getNumberString(accumulator) : description);
            }
        } else if (Binary.class.isInstance(operation)) {
            if (isPartialResult) {
                if (previousAppend == null) {
                    description += getNumberString(accumulator) + symbol;
                } else {
                    description += symbol;
                    previousAppend = null;
                }
            } else {
                description = (description.equals("") ? getNumberString(accumulator) : description) + symbol;
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

            description += getNumberString(accumulator);
        }
    }

}
