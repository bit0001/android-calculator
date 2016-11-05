package com.mathsistor.m.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class VariableTest {

    private String expectedDisplayedResult;
    private String expectedDisplayedDescription;

    @Rule
    public ActivityTestRule<CalculatorActivity> calculatorActivityActivityTestRule =
            new ActivityTestRule<>(CalculatorActivity.class);

    @After
    public void assertValuesOnDisplays() {
        onView(withId(R.id.operation_display)).check(matches(withText(expectedDisplayedDescription)));
        onView(withId(R.id.result_display)).check(matches(withText(expectedDisplayedResult)));
    }

    @Test
    public void test9PlusMEqualSquareRoot() throws Exception {
        expectedDisplayedResult = "3";
        expectedDisplayedDescription = "\u221a" + "(9+M)=";

        performPlusMEqualSquareRoot();
    }

    private void performPlusMEqualSquareRoot() {
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.get_M)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
        onView(withId(R.id.square_root_symbol)).perform(click());
    }

    @Test
    public void test9PlusMEqualSquareRoot7SetM() throws Exception {
        expectedDisplayedResult = "4";
        expectedDisplayedDescription = "\u221a" + "(9+M)=";

        performPlusMEqualSquareRoot();
        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.set_M)).perform(click());
    }

    @Test
    public void test9PlusMEqualSquareRoot7SetMPlus14Equals() throws Exception {
        expectedDisplayedResult = "18";
        expectedDisplayedDescription = "\u221a" + "(9+M)+14=";

        performPlusMEqualSquareRoot();
        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.set_M)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_1)).perform(click());
        onView(withId(R.id.digit_4)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
    }
}
