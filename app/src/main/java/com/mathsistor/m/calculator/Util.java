package com.mathsistor.m.calculator;

import java.util.HashMap;

/**
 * Created by m on 10/31/2016.
 */
public abstract class Util {

    public static HashMap<String, Operation> OPERATIONS;
    public static HashMap<String, String> SYMBOLS;

    static  {
        OPERATIONS =  new HashMap<>();
        OPERATIONS.put("\u03c0", Operation.PI_CONSTANT);
        OPERATIONS.put("e", Operation.E_CONSTANT);
        OPERATIONS.put("+/-", Operation.UNARY_NEGATIVE);
        OPERATIONS.put("\u221a", Operation.SQUARE_ROOT);
        OPERATIONS.put("\u221b", Operation.CUBE_ROOT);
        OPERATIONS.put("x" + "\u207b" + "\u00b9", Operation.X_POWER_MINUS_1);
        OPERATIONS.put("x" + "\u00b2", Operation.SQUARE);
        OPERATIONS.put("x" + "\u00b3", Operation.CUBE);
        OPERATIONS.put("sin", Operation.SIN);
        OPERATIONS.put("cos", Operation.COS);
        OPERATIONS.put("tan", Operation.TAN);
        OPERATIONS.put("e" + "\u02e3", Operation.EXP);
        OPERATIONS.put("10" + "\u02e3", Operation.TEN_POWER);
        OPERATIONS.put("log", Operation.LOG10);
        OPERATIONS.put("ln", Operation.LN);
        OPERATIONS.put("+", Operation.ADDITION);
        OPERATIONS.put("\u2212", Operation.SUBTRACTION);
        OPERATIONS.put("\u00d7", Operation.MULTIPLICATION);
        OPERATIONS.put("\u00f7", Operation.DIVISION);
        OPERATIONS.put("x" + "\u02b8", Operation.N_POWER);
        OPERATIONS.put("\u02b8" + "\u221a" + "x", Operation.N_ROOT);
        OPERATIONS.put("rand", Operation.RANDOM);
        OPERATIONS.put("=", Operation.EQUALS);

        SYMBOLS = new HashMap<>();
        SYMBOLS.put("x" + "\u207b" + "\u00b9", "\u207b" + "\u00b9");
        SYMBOLS.put("x" + "\u00b2", "\u00b2");
        SYMBOLS.put("x" + "\u00b3", "\u00b3");
        SYMBOLS.put("e" + "\u02e3", "e^");
        SYMBOLS.put("10" + "\u02e3", "10^");
    }

}
