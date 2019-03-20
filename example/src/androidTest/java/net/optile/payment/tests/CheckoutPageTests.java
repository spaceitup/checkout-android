/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.tests;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import net.optile.example.checkout.CheckoutActivity;


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
