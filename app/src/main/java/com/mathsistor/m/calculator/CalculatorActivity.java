package com.mathsistor.m.calculator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mathsistor.m.calculator.util.Maps;

import java.util.ArrayList;

import static com.mathsistor.m.calculator.util.Formatter.DELETE;
import static com.mathsistor.m.calculator.util.Formatter.UNDO;

public class CalculatorActivity extends AppCompatActivity {

    private boolean userIsInTheMiddleOfTyping;
    private TextView result_display;
    private CalculatorBrain brain;
    private TextView operation_display;
    private ArrayList<Object> savedProgram;
    private Button deleteUndoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        deleteUndoButton = (Button) findViewById(R.id.delete_undo);
        result_display = (TextView) findViewById(R.id.result_display);
        operation_display = (TextView) findViewById(R.id.operation_display);
        clear();
    }

    private void clear() {
        brain = new CalculatorBrain();
        result_display.setText("0");
        operation_display.setText("");
        userIsInTheMiddleOfTyping = false;
        deleteUndoButton.setText(UNDO);
    }

    public void touchDigit(View view) {
        String digit = ((Button) view).getText().toString();

        if (getDisplayString().equals("0") && digit.equals("0")) {
            return;
        }

        result_display.setText(userIsInTheMiddleOfTyping ? getDisplayString() + digit : digit);
        userIsInTheMiddleOfTyping = true;
        deleteUndoButton.setText(DELETE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void performOperation(View view) {
        if (userIsInTheMiddleOfTyping) {
            brain.setOperand(Double.parseDouble(getDisplayString()));
            userIsInTheMiddleOfTyping = false;
            deleteUndoButton.setText(UNDO);
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
        return Maps.formatNumber(brain.getResult());
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
            userIsInTheMiddleOfTyping = true;
            deleteUndoButton.setText(DELETE);
        }
    }

    public void deleteOrUndo(View view) {
        if (userIsInTheMiddleOfTyping) {
            StringBuilder currentText = new StringBuilder(getDisplayString());
            currentText.deleteCharAt(currentText.length() - 1);

            if (currentText.toString().isEmpty()) {
                result_display.setText("0");
                userIsInTheMiddleOfTyping = false;
                deleteUndoButton.setText(UNDO);
            } else {
                result_display.setText(currentText.toString());
            }
        } else {

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setVariableM(View view) {
        Button button = (Button) view;
        String variableName = button.getText().toString().substring(1);
        brain.setVariableValue(variableName, Double.parseDouble(getDisplayString()));
        brain.setInternalProgram(new ArrayList<>(brain.getInternalProgram()));
        userIsInTheMiddleOfTyping = false;
        deleteUndoButton.setText(UNDO);
        updateDisplays();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getVariableM(View view) {
        Button button = (Button) view;
        String variableName = button.getText().toString();
        brain.setOperand(variableName);
        userIsInTheMiddleOfTyping = false;
        deleteUndoButton.setText(UNDO);
        updateDisplays();
    }
}
