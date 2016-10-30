package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by m on 10/29/2016.
 */

public class CalculatorBrain {

    private double accumulator;
    private static HashMap<String, Operation> operations;
    private static HashMap<String, String> symbols;

    private PendingBinaryOperationInfo pending;
    private String description;
    private String previousAppend;
    private String baseDescription;

    CalculatorBrain() {
        this.description = "";
    }

    static {
        operations =  new HashMap<>();
        symbols = new HashMap<>();

        operations.put("\u03c0", Operation.PI_CONSTANT);
        operations.put("e", Operation.E_CONSTANT);
        operations.put("\u221a", Operation.SQUARE_ROOT);

        operations.put("x" + "\u00b2", Operation.SQUARE);
        symbols.put("x" + "\u00b2", "\u00b2");

        operations.put("x" + "\u207b" + "\u00b9", Operation.X_POWER_MINUS_1);
        symbols.put("x" + "\u207b" + "\u00b9", "\u207b" + "\u00b9");

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
        operations.put("rand", Operation.RANDOM);
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
                case X_POWER_MINUS_1:
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
                case RANDOM:
                    accumulator = operation.getSupplier().getAsDouble();
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

    private void setDescription(String operator) {
        Operation operation = operations.get(operator);

        switch (operation) {
            case SQUARE_ROOT:
            case SIN:
            case COS:
            case TAN:
            case LOG10:
            case LN:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = operator + "(" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = operator + "(" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = operator + "(" + getAccumulatorString() + ")";
                    } else {
                        description = operator + "(" + description + ")";
                    }
                }
                break;
            case X_POWER_MINUS_1:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = "(" + previousAppend + ")" + getSymbol(operator);
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = "(" + getAccumulatorString() + ")" + getSymbol(operator);
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = "(" + getAccumulatorString() + ")" + getSymbol(operator);
                    } else {
                        description = "(" + description + ")" + getSymbol(operator);
                    }
                }
                break;
            case SQUARE:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = "(" + previousAppend + ")" + getSymbol(operator);
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = "(" + getAccumulatorString() + ")" + getSymbol(operator);
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = "(" + getAccumulatorString() + ")" + getSymbol(operator);
                    } else {
                        description = "(" + description + ")" + getSymbol(operator);
                    }
                }
                break;
            case TEN_POWER:
            case EXP:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = operator.substring(0, operator.length() - 1) + "^(" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = operator.substring(0, operator.length() - 1) + "^(" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = operator.substring(0, operator.length() - 1) + "^(" + getAccumulatorString() + ")";
                    } else {
                        description = operator.substring(0, operator.length() - 1) + "^(" + description + ")";
                    }
                }
                break;
            case ADDITION:
            case SUBTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                if (isPartialResult()) {
                    description += getAccumulatorString() + operator;
                } else {
                    if (description.equals("")) {
                        description = getAccumulatorString() + operator;
                    } else {
                        description += operator;
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
                        description += "(" + operator + ")^";
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
    private String getSymbol(String operator) {
        return symbols.get(operator);
    }

    @NonNull
    private String getAccumulatorString() {
        String accumulatorString;
        if (accumulator == Math.PI) {
            accumulatorString = "\u03c0";
        } else if (accumulator == Math.E) {
            accumulatorString = "e";
        } else {
            double decimalPart = accumulator - Math.floor(accumulator);

            if (Math.abs(decimalPart) < 0.000001) {
                return String.valueOf((int) accumulator);
            }

            NumberFormat formatter = new DecimalFormat("#0.######");

            accumulatorString = formatter.format(accumulator);
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
