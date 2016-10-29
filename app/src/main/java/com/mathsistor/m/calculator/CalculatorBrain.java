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
