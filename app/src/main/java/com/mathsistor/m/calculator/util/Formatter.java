package com.mathsistor.m.calculator.util;

/**
 * Created by m on 11/4/2016.
 */

public class Formatter {
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
