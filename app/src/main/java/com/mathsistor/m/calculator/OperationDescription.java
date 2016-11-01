package com.mathsistor.m.calculator;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by m on 10/31/2016.
 */

public class OperationDescription {
    private String previousAppend;
    private String baseDescription;
    private String description;
    private HashMap<String, Operation> operations;
    private HashMap<String, String> symbols;

    public OperationDescription() {
        operations = Util.OPERATIONS;
        symbols = Util.SYMBOLS;
        description = "";
    }

    public void update(String operator, Double accumulator, boolean isPartialResult) {
        Operation operation = operations.get(operator);

        switch (operation) {
            case UNARY_NEGATIVE:
                if (isPartialResult) {
                    if (previousAppend != null) {
                        previousAppend = betweenParentheses("-" + previousAppend );
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = betweenParentheses("-" + getAccumulatorString(accumulator));
                        description += previousAppend;
                    }
                } else {
                    if (!description.equals("")) {
                        description = "-" + betweenParentheses(description);
                    }
                }
                break;
            case SQUARE_ROOT:
            case CUBE_ROOT:
            case SIN:
            case COS:
            case TAN:
            case LOG10:
            case LN:
                if (isPartialResult) {
                    if (previousAppend != null) {
                        previousAppend = operator + betweenParentheses(previousAppend);
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = operator + betweenParentheses(getAccumulatorString(accumulator));
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = operator + betweenParentheses(getAccumulatorString(accumulator));
                    } else {
                        description = operator + betweenParentheses(description);
                    }
                }
                break;
            case X_POWER_MINUS_1:
            case SQUARE:
            case CUBE:
                if (isPartialResult) {
                    if (previousAppend != null) {
                        previousAppend = betweenParentheses(previousAppend) + getSymbol(operator);
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = betweenParentheses(getAccumulatorString(accumulator)) + getSymbol(operator);
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = betweenParentheses(getAccumulatorString(accumulator)) + getSymbol(operator);
                    } else {
                        description = betweenParentheses(description) + getSymbol(operator);
                    }
                }
                break;
            case TEN_POWER:
            case EXP:
                if (isPartialResult) {
                    if (previousAppend != null) {
                        previousAppend = getSymbol(operator) + betweenParentheses(previousAppend);
                        description = baseDescription + previousAppend;
                    } else {
                        baseDescription = description;
                        previousAppend = getSymbol(operator) + betweenParentheses(getAccumulatorString(accumulator));
                        description += previousAppend;
                    }
                } else {
                    if (description.equals("")) {
                        description = getSymbol(operator) + betweenParentheses(getAccumulatorString(accumulator));
                    } else {
                        description = getSymbol(operator) + betweenParentheses(description);
                    }
                }
                break;
            case ADDITION:
            case SUBTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                if (isPartialResult) {
                    if (previousAppend == null) {
                        description += getAccumulatorString(accumulator) + operator;
                    } else {
                        description += operator;
                        previousAppend = null;
                    }
                } else {
                    if (description.equals("")) {
                        description = getAccumulatorString(accumulator) + operator;
                    } else {
                        description += operator;
                    }
                }
                break;
            case N_POWER:
                if (isPartialResult) {
                    if (previousAppend == null) {
                        description = betweenParentheses(description +  getAccumulatorString(accumulator)) + "^";
                    } else {
                        description = betweenParentheses(description) + "^";
                        previousAppend = null;
                    }
                } else {
                    if (description.equals("")) {
                        description = betweenParentheses(getAccumulatorString(accumulator)) + "^";
                    } else {
                        description = betweenParentheses(description) + "^";
                    }
                }
                break;
            case EQUALS:
                if (!isPartialResult) {
                    return;
                }

                if (previousAppend == null) {
                    description += getAccumulatorString(accumulator);
                }
                previousAppend = null;
                break;
        }
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    private String getAccumulatorString(Double accumulator) {
        if (accumulator == Math.PI) {
            return  "\u03c0";
        } else if (accumulator == Math.E) {
            return  "e";
        } else {
            NumberFormat formatter = new DecimalFormat("#0.######");
            return formatter.format(accumulator);
        }
    }

    private String betweenParentheses(String string) {
        return "(" + string + ")";
    }

    @NonNull
    private String getSymbol(String operator) {
        return symbols.get(operator);
    }

}
