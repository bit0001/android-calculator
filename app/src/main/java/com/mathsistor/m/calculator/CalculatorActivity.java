package com.mathsistor.m.calculator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private boolean userIsInTheMiddleOfTyping;
    private boolean floatingPointIsDisplayed;
    private TextView display;
    private CalculatorBrain brain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        display = (TextView) findViewById(R.id.result_display);

        clear();
    }

    private void clear() {
        brain = new CalculatorBrain();
        setDisplayValue(brain.getResult());
        userIsInTheMiddleOfTyping = false;
        floatingPointIsDisplayed = false;
    }

    public void touchDigit(View view) {
        String digit = ((Button) view).getText().toString();
        String textCurrentlyInDisplay = display.getText().toString();

        if (userIsInTheMiddleOfTyping) {
            display.setText(textCurrentlyInDisplay + digit);
        } else {
            display.setText(digit);
        }
        userIsInTheMiddleOfTyping = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(View view) {
        if (userIsInTheMiddleOfTyping) {
            brain.setOperand(getDisplayValue());
            userIsInTheMiddleOfTyping = false;
            floatingPointIsDisplayed = false;
        }

        String mathSymbol = ((Button) view).getText().toString();

        brain.performOperation(mathSymbol);
        setDisplayValue(brain.getResult());
    }

    public double getDisplayValue() {
        return Double.parseDouble(display.getText().toString());
    }

    public void setDisplayValue(double displayValue) {
        display.setText(String.valueOf(displayValue));
    }

    public void clear(View view) {
        clear();
    }

    public void addDecimalPoint(View view) {
        if (!floatingPointIsDisplayed) {
            if (userIsInTheMiddleOfTyping) {
                display.setText(((int) getDisplayValue()) + ".");
            } else {
                display.setText("0.");
            }
            floatingPointIsDisplayed = true;
            userIsInTheMiddleOfTyping = true;
        }
    }
}
