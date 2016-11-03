package com.mathsistor.m.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by m on 10/29/2016.
 */

public class CalculatorBrain {

    private double accumulator;
    private PendingBinaryOperationInfo pending;
    private OperationDescription description;
    private HashMap<String, Operation> operations;
    private ArrayList<Object> internalProgram;

    CalculatorBrain() {
        operations = Util.OPERATIONS;
        description = new OperationDescription();
        internalProgram = new ArrayList<>();
    }

    public void setOperand(double operand) {
        accumulator = operand;
        internalProgram.add(operand);

        if (!isPartialResult()) {
            description = new OperationDescription();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(String symbol) {
        Operation operation = operations.get(symbol);
        if (operation != null) {
            internalProgram.add(symbol);
            description.update(symbol, accumulator, isPartialResult());
            computeResult(operation);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void computeResult(Operation operation) {
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
        return description.getDescription();
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
