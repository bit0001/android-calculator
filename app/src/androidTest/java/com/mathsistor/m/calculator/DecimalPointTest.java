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
public class DecimalPointTest {

    String expectedDisplayedResult;

    @Rule
    public ActivityTestRule<CalculatorActivity> calculatorActivityActivityTestRule =
            new ActivityTestRule<>(CalculatorActivity.class);

    @After
    public void assertValueOnResultDisplay() {
        onView(withId(R.id.result_display)).check(matches(withText(expectedDisplayedResult)));
    }


    @Test
    public void testNumberPoint() throws Exception {
        expectedDisplayedResult = "2.";

        onView(withId(R.id.digit_2)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
    }

    @Test
    public void testNumberPointNumber() throws Exception {
        expectedDisplayedResult = "2.1";

        onView(withId(R.id.digit_2)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.digit_1)).perform(click());
    }

    @Test
    public void testPoint() throws Exception {
        expectedDisplayedResult = "0.";

        onView(withId(R.id.decimal_point)).perform(click());
    }

    @Test
    public void testPointPoint() throws Exception {
        expectedDisplayedResult = "0.";

        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
    }

    @Test
    public void testNumberPointPoint() throws Exception {
        expectedDisplayedResult = "6.";

        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
    }

    @Test
    public void testNumberPointNumberPoint() throws Exception {
        expectedDisplayedResult = "6.5";

        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.digit_5)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
    }

    public void testNumberPointNumberPointNumber() throws Exception {
        expectedDisplayedResult = "6.57";

        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.digit_5)).perform(click());
        onView(withId(R.id.decimal_point)).perform(click());
        onView(withId(R.id.digit_7)).perform(click());
    }
}
