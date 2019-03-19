package net.optile.payment.tests;


import net.optile.example.checkout.CheckoutActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.test.filters.LargeTest;

import static android.support.test.espresso.Espresso.*;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckoutPageTests {
    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule = new ActivityTestRule<>(
        CheckoutActivity.class);

    @Test
    public void defaultThemeLabelTest() {
        onView(withText("Default Theme")).check(matches(isDisplayed()));

    }
}
