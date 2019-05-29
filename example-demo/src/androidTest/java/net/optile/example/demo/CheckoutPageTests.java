/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.payment.test.action.CustomScrollTo;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckoutPageTests {
    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule = new ActivityTestRule<>(
        CheckoutActivity.class, true, false);

    @Test
    public void openPaymentListTest() throws JSONException, IOException {
        Intents.init();

        ListService service = getListService();
        ListConfig config = service.createListConfig(net.optile.example.demo.test.R.raw.listtemplate);
        String listUrl = service.createListUrl(config);

        Intent intent = new Intent();
        intent.putExtra(BaseActivity.EXTRA_LISTURL, listUrl);
        activityRule.launchActivity(intent);
        onView(withId(R.id.button_checkout)).perform(new CustomScrollTo(), click());

        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_parent)).check(matches(isDisplayed()));

        Intents.release();
    }

    private ListService getListService() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);
        return ListService.createInstance(url, auth);
    }
}
