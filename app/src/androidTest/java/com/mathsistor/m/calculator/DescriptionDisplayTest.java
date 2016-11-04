package com.mathsistor.m.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class DescriptionDisplayTest {

    private String expectedDisplayedDescription;
    private String expectedDisplayedResult;

    @Rule
    public ActivityTestRule<CalculatorActivity> calculatorActivityActivityTestRule =
            new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void test7Plus() throws Exception {
        expectedDisplayedDescription = "7+...";
        expectedDisplayedResult = "7";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9() throws Exception {
        expectedDisplayedDescription = "7+...";
        expectedDisplayedResult = "9";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());

        assertValuesOnDisplays();
    }

    private void assertValuesOnDisplays() {
        onView(withId(R.id.operation_display)).check(matches(withText(expectedDisplayedDescription)));
        onView(withId(R.id.result_display)).check(matches(withText(expectedDisplayedResult)));
    }

    @Test
    public void test7Plus9Equal() throws Exception {
        expectedDisplayedDescription = "7+9=";
        expectedDisplayedResult = "16";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9EqualSquareRoot() throws Exception {
        expectedDisplayedDescription = "\u221a" + "(7+9)=";
        expectedDisplayedResult = "4";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
        onView(withId(R.id.square_root_symbol)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9SquareRoot() throws Exception {
        expectedDisplayedDescription = "7+" + "\u221a" + "(9)...";
        expectedDisplayedResult = "3";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.square_root_symbol)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9SquareRootEqual() throws Exception {
        expectedDisplayedDescription = "7+" + "\u221a" + "(9)=";
        expectedDisplayedResult = "10";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.square_root_symbol)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9EqualPlus6Plus3Equal() throws Exception {
        expectedDisplayedDescription = "7+9+6+3=";
        expectedDisplayedResult = "25";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_3)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7Plus9EqualSquareRoot6Plus3() throws Exception {
        expectedDisplayedDescription = "6+3=";
        expectedDisplayedResult = "9";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_9)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
        onView(withId(R.id.square_root_symbol)).perform(click());
        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_3)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test5Plus6Equal73() throws Exception {
        expectedDisplayedDescription = "5+6=";
        expectedDisplayedResult = "73";

        onView(withId(R.id.digit_5)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_6)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());
        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.digit_3)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test7PlusEqual() throws Exception {
        expectedDisplayedDescription = "7+7=";
        expectedDisplayedResult = "14";

        onView(withId(R.id.digit_7)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test4TimesPiEqual() throws Exception {
        expectedDisplayedDescription = "4" + "\u00d7" + "\u03c0" + "=";
        expectedDisplayedResult = Util.formatNumber(4 * Math.PI);

        onView(withId(R.id.digit_4)).perform(click());
        onView(withId(R.id.multiplication_sign)).perform(click());
        onView(withId(R.id.pi_letter)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }

    @Test
    public void test4Plus5Times3Equal() throws Exception {
        expectedDisplayedDescription = "4+5" + "\u00d7" + "3=";
        expectedDisplayedResult = "27";

        onView(withId(R.id.digit_4)).perform(click());
        onView(withId(R.id.plus_sign)).perform(click());
        onView(withId(R.id.digit_5)).perform(click());
        onView(withId(R.id.multiplication_sign)).perform(click());
        onView(withId(R.id.digit_3)).perform(click());
        onView(withId(R.id.equal_sign)).perform(click());

        assertValuesOnDisplays();
    }
}
