package com.mathsistor.m.calculator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    private boolean userIsInTheMiddleOfTyping;
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
        result_display.setText("0");
        operation_display.setText("");
        userIsInTheMiddleOfTyping = false;
    }

    public void touchDigit(View view) {
        String digit = ((Button) view).getText().toString();
        result_display.setText(userIsInTheMiddleOfTyping ? getDisplayValueString() + digit : digit);
        userIsInTheMiddleOfTyping = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(View view) {
        if (userIsInTheMiddleOfTyping) {
            brain.setOperand(Double.parseDouble(getDisplayValueString()));
            userIsInTheMiddleOfTyping = false;
        }

        String operator = ((Button) view).getText().toString();
        brain.performOperation(operator);
        result_display.setText(getResultFormatted());
        operation_display.setText(brain.getDescription() + (brain.isPartialResult() ? "..." : "="));
    }

    private String getResultFormatted() {
        return new DecimalFormat("#0.######").format(brain.getResult());
    }

    public void clearEverything(View view) {
        clear();
    }

    public void addDecimalPoint(View view) {
        if (userIsInTheMiddleOfTyping) {
            if (!getDisplayValueString().contains(".")) {
                result_display.setText(getDisplayValueString() + ".");
            }
        } else {
            result_display.setText("0.");
        }

        userIsInTheMiddleOfTyping = true;
    }

    public void delete(View view) {
        StringBuilder currentText = new StringBuilder(getDisplayValueString());
        currentText.deleteCharAt(currentText.length() - 1);

        if (currentText.toString().isEmpty()) {
            result_display.setText("0");
            userIsInTheMiddleOfTyping = false;
        } else {
            result_display.setText(currentText.toString());
        }
    }

    private String getDisplayValueString() {
        return result_display.getText().toString();
    }

}
