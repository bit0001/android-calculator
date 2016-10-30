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
    private TextView result_display;
    private CalculatorBrain brain;
    private TextView operation_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        result_display = (TextView) findViewById(R.id.result_display);
        operation_display = (TextView) findViewById(R.id.operation_display);

        clear();
    }

    private void clear() {
        brain = new CalculatorBrain();
        initializeDisplays();
        userIsInTheMiddleOfTyping = false;
        floatingPointIsDisplayed = false;
    }

    public void touchDigit(View view) {
        String digit = ((Button) view).getText().toString();
        String textCurrentlyInDisplay = result_display.getText().toString();

        if (userIsInTheMiddleOfTyping) {
            result_display.setText(textCurrentlyInDisplay + digit);
        } else {
            result_display.setText(digit);
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
        setDisplays(brain.getResult());
    }

    public double getDisplayValue() {
        return Double.parseDouble(result_display.getText().toString());
    }

    public void initializeDisplays() {
        result_display.setText("0");
        operation_display.setText("0");
    }

    public void setDisplays(double displayValue) {
        result_display.setText(String.valueOf(displayValue));
        operation_display.setText(brain.getDescription() + (brain.isPartialResult() ? "..." : "="));
    }

    public void clear(View view) {
        clear();
    }

    public void addDecimalPoint(View view) {
        if (!floatingPointIsDisplayed) {
            if (userIsInTheMiddleOfTyping) {
                result_display.setText(((int) getDisplayValue()) + ".");
            } else {
                result_display.setText("0.");
            }
            floatingPointIsDisplayed = true;
            userIsInTheMiddleOfTyping = true;
        }
    }
}
