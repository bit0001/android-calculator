package com.mathsistor.m.calculator;

import java.util.HashMap;

/**
 * Created by m on 10/31/2016.
 */
public class Operations {
    private static Operations ourInstance = new Operations();

    public static Operations getInstance() {
        return ourInstance;
    }
    public HashMap<String, Operation> operations;

    private Operations() {
        operations =  new HashMap<>();
        operations.put("\u03c0", Operation.PI_CONSTANT);
        operations.put("e", Operation.E_CONSTANT);
        operations.put("+/-", Operation.UNARY_NEGATIVE);
        operations.put("\u221a", Operation.SQUARE_ROOT);
        operations.put("\u221b", Operation.CUBE_ROOT);
        operations.put("x" + "\u207b" + "\u00b9", Operation.X_POWER_MINUS_1);
        operations.put("x" + "\u00b2", Operation.SQUARE);
        operations.put("x" + "\u00b3", Operation.CUBE);
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
}
