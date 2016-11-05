package com.mathsistor.m.calculator.util;

public class Formatter {
    public static final String UNDO = "undo";
    public static final String DELETE = "\u2190";

    public static String betweenParentheses(String string) {
        return "(" + string + ")";
    }

    public static String getNumberString(Double accumulator) {
        if (accumulator == Math.PI) {
            return  "\u03c0";
        } else if (accumulator == Math.E) {
            return  "e";
        } else {
            return Maps.formatNumber(accumulator);
        }
    }
}
