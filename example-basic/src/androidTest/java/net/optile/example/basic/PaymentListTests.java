/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.basic;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
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
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PaymentListTests {
    @Rule
    public ActivityTestRule<BasicActivity> activityRule = new ActivityTestRule<>(
        BasicActivity.class);

    @Test
    public void openPaymentListTest() throws JSONException, IOException {
        Intents.init();
        openPaymentList();
        Intents.release();
    }

    private void openPaymentList() throws JSONException, IOException {
        ListService service = getListService();
        ListConfig config = service.createListConfig(net.optile.example.basic.test.R.raw.listtemplate);
        String listUrl = service.createListUrl(config);
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());

        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_paymentlist)).check(matches(isDisplayed()));
    }

    private ListService getListService() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);
        return ListService.createInstance(url, auth);
    }
}

