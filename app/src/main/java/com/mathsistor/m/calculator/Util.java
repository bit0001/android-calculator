package com.mathsistor.m.calculator;

import com.mathsistor.m.calculator.operation.Binary;
import com.mathsistor.m.calculator.operation.Constant;
import com.mathsistor.m.calculator.operation.Equal;
import com.mathsistor.m.calculator.operation.Operation;
import com.mathsistor.m.calculator.operation.Random;
import com.mathsistor.m.calculator.operation.Unary;

import java.text.DecimalFormat;
import java.util.HashMap;

public abstract class Util {

    public static HashMap<String, Operation> OPERATIONS;
    public static HashMap<String, String> SYMBOLS;

    static  {
        OPERATIONS =  new HashMap<>();
        OPERATIONS.put("\u03c0", new Constant(Math.PI));
        OPERATIONS.put("e", new Constant(Math.E));
        OPERATIONS.put("+/-", new Unary(a -> -a));
        OPERATIONS.put("\u221a", new Unary(Math::sqrt));
        OPERATIONS.put("\u221b", new Unary(a -> Math.pow(a, 1.0 / 3)));
        OPERATIONS.put("x" + "\u207b" + "\u00b9", new Unary(a -> 1 / a));
        OPERATIONS.put("x" + "\u00b2", new Unary(a -> a * a));
        OPERATIONS.put("x" + "\u00b3", new Unary(a -> a * a * a));
        OPERATIONS.put("sin", new Unary(Math::sin));
        OPERATIONS.put("cos", new Unary(Math::cos));
        OPERATIONS.put("tan", new Unary(Math::tan));
        OPERATIONS.put("e" + "\u02e3", new Unary(Math::exp));
        OPERATIONS.put("10" + "\u02e3", new Unary(a -> Math.pow(a, 10)));
        OPERATIONS.put("log", new Unary(Math::log10));
        OPERATIONS.put("ln", new Unary(Math::log));
        OPERATIONS.put("+", new Binary((a, b) -> a + b));
        OPERATIONS.put("\u2212", new Binary((a, b) -> a - b));
        OPERATIONS.put("\u00d7", new Binary((a, b) -> a * b));
        OPERATIONS.put("\u00f7", new Binary((a, b) -> a / b));
        OPERATIONS.put("x" + "\u02b8", new Binary(Math::pow));
        OPERATIONS.put("\u02b8" + "\u221a" + "x", new Binary((a, b) -> Math.pow(a, 1 / b)));
        OPERATIONS.put("rand", new Random());
        OPERATIONS.put("=", new Equal());

        SYMBOLS = new HashMap<>();
        SYMBOLS.put("x" + "\u207b" + "\u00b9", "\u207b" + "\u00b9");
        SYMBOLS.put("x" + "\u00b2", "\u00b2");
        SYMBOLS.put("x" + "\u00b3", "\u00b3");
        SYMBOLS.put("e" + "\u02e3", "e^");
        SYMBOLS.put("10" + "\u02e3", "10^");
    }

    public static String formatNumber(Double number) {
        return new DecimalFormat("#0.######").format(number);
    }
}
