/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.example.sdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.payoneer.mrs.payment.ui.page.PaymentListActivity;

import com.payoneer.mrs.example.sdk.BuildConfig;
import com.payoneer.mrs.example.sdk.R;
import com.payoneer.mrs.sharedtest.service.ListService;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleSdkTests {
    @Rule
    public ActivityTestRule<ExampleSdkActivity> activityRule = new ActivityTestRule<>(
        ExampleSdkActivity.class);

    @Test
    public void sdkPageVisibleTest() {
        onView(ViewMatchers.withId(R.id.activity_examplesdk)).check(matches(isDisplayed()));
    }

    @Test
    public void openPaymentListTest() throws JSONException, IOException {
        Intents.init();
        openPaymentList();
        Intents.release();
    }

    private void openPaymentList() throws JSONException, IOException {
        String baseUrl = BuildConfig.paymentapi_baseurl;
        String authHeader = BuildConfig.paymentapi_authheader;
        String listUrl = ListService.createListUrl(com.payoneer.mrs.example.sdk.test.R.raw.listtemplate, false, baseUrl, authHeader);

        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());

        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_paymentlist)).check(matches(isDisplayed()));
    }


}
