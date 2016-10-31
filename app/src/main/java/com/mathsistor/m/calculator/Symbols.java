package com.mathsistor.m.calculator;

import java.util.HashMap;

/**
 * Created by m on 10/31/2016.
 */
public class Symbols {
    private static Symbols ourInstance = new Symbols();

    public static Symbols getInstance() {
        return ourInstance;
    }
    public HashMap<String, String> symbols;

    private Symbols() {
        symbols = new HashMap<>();
        symbols.put("x" + "\u207b" + "\u00b9", "\u207b" + "\u00b9");
        symbols.put("x" + "\u00b2", "\u00b2");
        symbols.put("x" + "\u00b3", "\u00b3");
        symbols.put("e" + "\u02e3", "e^");
        symbols.put("10" + "\u02e3", "10^");
    }
}
