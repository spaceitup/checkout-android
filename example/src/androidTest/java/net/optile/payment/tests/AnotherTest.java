package net.optile.payment.tests;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.optile.example.checkout.CheckoutActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AnotherTest {

    @Rule
    public ActivityTestRule<CheckoutActivity> mActivityRule = new ActivityTestRule<>(
            CheckoutActivity.class);


    @Test
    public void AnotherTest(){
        onView(withText("Custom Theme")).check(matches(isDisplayed()));

    }
}
