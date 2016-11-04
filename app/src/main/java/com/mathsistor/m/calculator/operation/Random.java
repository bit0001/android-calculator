package com.mathsistor.m.calculator.operation;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.function.DoubleSupplier;

/**
 * Created by m on 11/3/2016.
 */

public class Random extends Operation {
    private DoubleSupplier supplier;

    public Random() {
        this.supplier = Math::random;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public double getRandomNumber() {
        return supplier.getAsDouble();
    }
}
