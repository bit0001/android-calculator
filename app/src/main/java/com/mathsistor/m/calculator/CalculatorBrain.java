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

import java.util.ArrayList;


public class CalculatorBrain {

    private double accumulator;
    private PendingBinaryOperationInfo pending;
    private OperationDescription description;
    private ArrayList<Object> internalProgram;

    CalculatorBrain() {
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
        Operation operation = Util.OPERATIONS.get(symbol);
        if (operation != null) {
            internalProgram.add(symbol);
            description.update(symbol, accumulator, isPartialResult());
            computeResult(operation);
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
