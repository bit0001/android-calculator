package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
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
    private PendingBinaryOperationInfo pending;
    private String description;
    private String previousAppend;
    private String baseDescription;
    private HashMap<String, Operation> operations;
    private HashMap<String, String> symbols;

    CalculatorBrain() {
        this.description = "";
        operations = Operations.getInstance().operations;
        symbols = Symbols.getInstance().symbols;
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
                case UNARY_NEGATIVE:
                case X_POWER_MINUS_1:
                case SQUARE_ROOT:
                case CUBE_ROOT:
                case SQUARE:
                case CUBE:
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
            case UNARY_NEGATIVE:
                if (isPartialResult()) {
                    if (previousAppend != null) {
                        previousAppend = "(" + "-" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = "(" + "-" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (!description.equals("")) {
                        description = "-" + "(" + description + ")";
                    }
                }
                break;
            case SQUARE_ROOT:
            case CUBE_ROOT:
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
            case SQUARE:
            case CUBE:
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
                        previousAppend = getSymbol(operator) + "(" + previousAppend + ")";
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = getSymbol(operator) + "(" + getAccumulatorString() + ")";
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = getSymbol(operator) + "(" + getAccumulatorString() + ")";
                    } else {
                        description = getSymbol(operator) + "(" + description + ")";
                    }
                }
                break;
            case ADDITION:
            case SUBTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                if (isPartialResult()) {
                    if (previousAppend == null) {
                        description += getAccumulatorString() + operator;
                    } else {
                        description += operator;
                        previousAppend = null;
                    }
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
                    if (previousAppend == null) {
                        description = "(" + description +  getAccumulatorString() + ")^";
                    } else {
                        description = "(" + description + ")^";
                        previousAppend = null;
                    }
                } else {
                    if (description.equals("")) {
                        description = "(" + getAccumulatorString() + ")^";
                    } else {
                        description = "(" + description + ")^";
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
