package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mathsistor.m.calculator.operation.Binary;
import com.mathsistor.m.calculator.operation.Constant;
import com.mathsistor.m.calculator.operation.Equal;
import com.mathsistor.m.calculator.operation.Operation;
import com.mathsistor.m.calculator.operation.Random;
import com.mathsistor.m.calculator.operation.Unary;
import com.mathsistor.m.calculator.util.Maps;

import java.util.ArrayList;

import static com.mathsistor.m.calculator.util.Formatter.betweenParentheses;
import static com.mathsistor.m.calculator.util.Formatter.getNumberString;


public class CalculatorBrain {

    private ArrayList<Object> internalProgram;
    private PendingBinaryOperationInfo pending;
    private String description;
    private String previousAppend;
    private String baseDescription;
    private double accumulator;

    CalculatorBrain() {
        description = "";
        internalProgram = new ArrayList<>();
    }

    public void setOperand(double operand) {
        accumulator = operand;
        internalProgram.add(operand);

        if (!isPartialResult()) {
            description = "";
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(String symbol) {
        Operation operation = Maps.OPERATIONS.get(symbol);
        if (operation != null) {
            internalProgram.add(symbol);
            updateDescription(symbol);
            computeResult(operation);
        }
    }

    private void updateDescription(String symbol) {
        Operation operation = Maps.OPERATIONS.get(symbol);
        if (Constant.class.isInstance(operation)) {
        } else if (Unary.class.isInstance(operation)) {
            if (isPartialResult()) {
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
            if (isPartialResult()) {
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
            if (!isPartialResult()) {
                return;
            }

            if (previousAppend != null) {
                previousAppend = null;
                return;
            }

            description += getNumberString(accumulator);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void computeResult(Operation operation) {
        if (Constant.class.isInstance(operation)) {
            accumulator = ((Constant) operation).getConstant();
        } else if (Unary.class.isInstance(operation)) {
            accumulator = ((Unary) operation).getUnaryOperator().applyAsDouble(accumulator);
        } else if (Binary.class.isInstance(operation)) {
            executePendingBinaryOperation();
            pending = new PendingBinaryOperationInfo(((Binary) operation).getBinaryOperator(), accumulator);
        } else if (Random.class.isInstance(operation)) {
            accumulator = ((Random) operation).getRandomNumber();
        } else if (Equal.class.isInstance(operation)) {
            executePendingBinaryOperation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void executePendingBinaryOperation() {
        if (pending != null) {
            accumulator = pending.getBinaryOperator().applyAsDouble(pending.getFirstOperand(), accumulator);
            pending = null;
        }
    }

    public double getResult() {
        return accumulator;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPartialResult() {
        return pending != null;
    }

    public ArrayList<Object> getInternalProgram() {
        return internalProgram;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setInternalProgram(ArrayList<Object> internalProgram) {
        clear();
        for (Object op: internalProgram) {
            if (Double.class.isInstance(op)) {
                setOperand((Double) op);
            } else if (String.class.isInstance(op)) {
                performOperation((String) op);
            }
        }
    }

    private void clear() {
        accumulator = 0;
        pending = null;
        internalProgram.clear();
    }
}
