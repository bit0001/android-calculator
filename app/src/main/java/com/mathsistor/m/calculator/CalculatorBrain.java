package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;

/**
 * Created by m on 10/29/2016.
 */

public class CalculatorBrain {

    private double accumulator;
    private static HashMap<String, Operation> operations;
    private PendingBinaryOperationInfo pending;

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
        operations.put("+", Operation.ADDITION);
        operations.put("\u2212", Operation.SUBTRACTION);
        operations.put("\u00d7", Operation.MULTIPLICATION);
        operations.put("\u00f7", Operation.DIVISION);
        operations.put("=", Operation.EQUALS);
    }

    public void setOperand(double operand) {
        accumulator = operand;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(String symbol) {
        Operation operation = operations.get(symbol);

        if (operation != null) {
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
                    accumulator = operation.getUnaryOperator().applyAsDouble(accumulator);
                    break;
                case ADDITION:
                case SUBTRACTION:
                case MULTIPLICATION:
                case DIVISION:
                    executePendingBinaryOperation();
                    pending = new PendingBinaryOperationInfo(operation.getBinaryOperator(), accumulator);
                    break;
                case EQUALS:
                    executePendingBinaryOperation();
            }
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
}
