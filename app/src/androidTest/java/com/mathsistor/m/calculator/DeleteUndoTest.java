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
public class DeleteUndoTest {

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
    public void testMCos() throws Exception {
        expectedDisplayedResult = "1";
        expectedDisplayedDescription = "cos(M)=";

        onView(withId(R.id.get_M)).perform(click());
        onView(withId(R.id.cos_button)).perform(click());
    }

    @Test
    public void testMCosPiSetMUndo() throws Exception {
        expectedDisplayedResult = "-1";
        expectedDisplayedDescription = "cos(M)=";

        onView(withId(R.id.get_M)).perform(click());
        onView(withId(R.id.cos_button)).perform(click());
        onView(withId(R.id.pi_letter)).perform(click());
        onView(withId(R.id.set_M)).perform(click());
        onView(withId(R.id.delete_undo)).perform(click());
    }
}
