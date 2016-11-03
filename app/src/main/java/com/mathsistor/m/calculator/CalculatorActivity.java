package com.mathsistor.m.calculator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {

    private boolean userIsInTheMiddleOfTyping;
    private TextView result_display;
    private CalculatorBrain brain;
    private TextView operation_display;
    private ArrayList<Object> savedProgram;

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
        result_display.setText("0");
        operation_display.setText("");
        userIsInTheMiddleOfTyping = false;
    }

    public void touchDigit(View view) {
        String digit = ((Button) view).getText().toString();
        result_display.setText(userIsInTheMiddleOfTyping ? getDisplayString() + digit : digit);
        userIsInTheMiddleOfTyping = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(View view) {
        if (userIsInTheMiddleOfTyping) {
            brain.setOperand(Double.parseDouble(getDisplayString()));
            userIsInTheMiddleOfTyping = false;
        }

        String operator = ((Button) view).getText().toString();
        brain.performOperation(operator);
        updateDisplays();
    }

    private void updateDisplays() {
        result_display.setText(getResultFormatted());
        operation_display.setText(brain.getDescription() + (brain.isPartialResult() ? "..." : "="));
    }

    private String getResultFormatted() {
        return Util.formatNumber(brain.getResult());
    }

    public void clearEverything(View view) {
        clear();
    }

    public void addDecimalPoint(View view) {
        if (userIsInTheMiddleOfTyping) {
            if (!getDisplayString().contains(".")) {
                result_display.setText(getDisplayString() + ".");
            }
        } else {
            result_display.setText("0.");
        }

        userIsInTheMiddleOfTyping = true;
    }

    public void delete(View view) {
        if (userIsInTheMiddleOfTyping) {
            StringBuilder currentText = new StringBuilder(getDisplayString());
            currentText.deleteCharAt(currentText.length() - 1);

            if (currentText.toString().isEmpty()) {
                result_display.setText("0");
                userIsInTheMiddleOfTyping = false;
            } else {
                result_display.setText(currentText.toString());
            }
        }
    }

    private String getDisplayString() {
        return result_display.getText().toString();
    }

    public void save(View view) {
        savedProgram = brain.getInternalProgram();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void restore(View view) {
        if (savedProgram !=  null) {
            brain.setInternalProgram(savedProgram);
            updateDisplays();
        }
    }
}
