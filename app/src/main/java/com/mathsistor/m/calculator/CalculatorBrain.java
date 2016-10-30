package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.HashMap;

/**
 * Created by m on 10/29/2016.
 */

public class CalculatorBrain {

    private double accumulator;
    private static HashMap<String, Operation> operations;
    private PendingBinaryOperationInfo pending;
    private String description;
    private String previousAppend;
    private String baseDescription;

    CalculatorBrain() {
        this.description = "";
    }

    static {
        operations =  new HashMap<>();
        operations.put("\u03c0", Operation.PI_CONSTANT);
        operations.put("e", Operation.E_CONSTANT);
        operations.put("\u221a", Operation.SQUARE_ROOT);
        operations.put("x" + "\u00b2", Operation.SQUARE);
        operations.put("sin", Operation.SIN);
        operations.put("cos", Operation.COS);
        operations.put("tan", Operation.TAN);
        operations.put("e" + "\u02e3", Operation.EXP);
        operations.put("10" + "\u02e3", Operation.TEN_POWER);
        operations.put("log", Operation.LOG10);
        operations.put("ln", Operation.LN);
        operations.put("+", Operation.ADDITION);
        operations.put("\u2212", Operation.SUBTRACTION);
        operations.put("\u00d7", Operation.MULTIPLICATION);
        operations.put("\u00f7", Operation.DIVISION);
        operations.put("x" + "\u02b8", Operation.N_POWER);
        operations.put("\u02b8" + "\u221a" + "x", Operation.N_ROOT);
        operations.put("=", Operation.EQUALS);
    }

    public void setOperand(double operand) {
        accumulator = operand;
        if (!isPartialResult()) {
            this.description = "";
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(String symbol) {
        Operation operation = operations.get(symbol);

        if (operation != null) {
            setDescription(symbol);

            switch (operation) {
                case PI_CONSTANT:
                case E_CONSTANT:
                    accumulator = operation.getConstant();
                    break;
                case SQUARE_ROOT:
                case SQUARE:
                case SIN:
                case COS:
                case TAN:
                case EXP:
                case TEN_POWER:
                case LOG10:
                case LN:
                    accumulator = operation.getUnaryOperator().applyAsDouble(accumulator);
                    break;
                case ADDITION:
                case SUBTRACTION:
                case MULTIPLICATION:
                case DIVISION:
                case N_POWER:
                case N_ROOT:
                    executePendingBinaryOperation();
                    pending = new PendingBinaryOperationInfo(operation.getBinaryOperator(), accumulator);
                    break;
                case EQUALS:
                    executePendingBinaryOperation();
            }
        }

    }

    private void setDescription(String symbol) {
        Operation operation = operations.get(symbol);

        switch (operation) {
            case SQUARE_ROOT:
            case SIN:
            case COS:
            case TAN:
            case LOG10:
            case LN:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = symbol + "(" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = symbol + "(" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = symbol + "(" + getAccumulatorString() + ")";
                    } else {
                        description = symbol + "(" + description + ")";
                    }
                }
                break;
            case SQUARE:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = "(" + previousAppend + ")" + "\u00b2";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = "(" + getAccumulatorString() + ")" + "\u00b2";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = "(" + getAccumulatorString() + ")" + "\u00b2";
                    } else {
                        description = "(" + description + ")" + "\u00b2";
                    }
                }
                break;
            case TEN_POWER:
            case EXP:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = symbol.substring(0, symbol.length() - 1) + "^(" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = symbol.substring(0, symbol.length() - 1) + "^(" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = symbol.substring(0, symbol.length() - 1) + "^(" + getAccumulatorString() + ")";
                    } else {
                        description = symbol.substring(0, symbol.length() - 1) + "^(" + description + ")";
                    }
                }
                break;
            case ADDITION:
            case SUBTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                if (isPartialResult()) {
                    description += getAccumulatorString() + symbol;
                } else {
                    if (description.equals("")) {
                        description = getAccumulatorString() + symbol;
                    } else {
                        description += symbol;
                    }
                }
                break;
            case N_POWER:
                if (isPartialResult()) {
                    description += "(" + getAccumulatorString() + ")^";
                } else {
                    if (description.equals("")) {
                        description = "(" + getAccumulatorString() + ")^";
                    } else {
                        description += "(" + symbol + ")^";
                    }
                }
                break;
            case EQUALS:
                if (!isPartialResult()) {
                    return;
                }

                if (previousAppend == null) {
                    description += getAccumulatorString();
                }
                previousAppend = null;
                break;
        }

    }

    @NonNull
    private String getAccumulatorString() {
        String accumulatorString;
        if (accumulator == Math.PI) {
            accumulatorString = "\u03c0";
        } else if (accumulator == Math.E) {
            accumulatorString = "e";
        } else {
            accumulatorString = String.valueOf(accumulator);
        }
        return accumulatorString;
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
}
