/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.tests;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import net.optile.example.checkout.CheckoutActivity;
import net.optile.example.checkout.R;
import net.optile.payment.list.ListConfig;
import net.optile.payment.list.ListService;
import net.optile.payment.ui.page.PaymentPageActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class PaymentPageTests {
    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule = new ActivityTestRule<>(
        CheckoutActivity.class);

    @Test
    public void openPaymentPageTest() throws JSONException, IOException {
        Intents.init();
        ListService listService = new ListService();
        ListConfig config = listService.createNewBodyConfig();
        config.setPrice("1.0");
        String listUrl = listService.createConfigListUrl(config);
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());
        intended(hasComponent(PaymentPageActivity.class.getName()));
        Intents.release();
    }
}

