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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.optile.payment.test.view.PaymentActions.actionOnViewInWidget;
import static net.optile.payment.test.view.PaymentMatchers.isCardWithTestId;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.test.view.PaymentActions;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleDemoTests {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);
    
    @Test
    public void successfulDirectChargeTest() throws JSONException, IOException {
        Intents.init();

        ListService service = getListService();
        ListConfig config = service.createListConfig(net.optile.example.demo.test.R.raw.listtemplate);
        config.setPresetFirst(false);
        String listUrl = service.createListUrl(config);

        // enter the listUrl in the settings screen and click the button
        onView(withId(R.id.layout_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_settings)).perform(click());
        
        // Wait for CheckoutActivity to be visible and click the pay button
        intended(hasComponent(CheckoutActivity.class.getName()));
        onView(withId(R.id.layout_checkout)).check(matches(isDisplayed()));
        onView(withId(R.id.button_checkout)).perform(PaymentActions.scrollToView(), click());

        // Obtain the PaymentListActivity 
        intended(hasComponent(PaymentListActivity.class.getName()));
        PaymentListActivity activity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource idlingResource = activity.getLoadingIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

        // Wait for the LoadingIdlingResource 
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);        
        onView(list).check(matches(isDisplayed()));

        // Check and fill the payment card
        onView(list).check(matches(isCardWithTestId(0, "label_header")));
        onView(list).check(matches(isCardWithTestId(1, "card_group")));
        onView(list).perform(actionOnItemAtPosition(1, click()));

        onView(list).perform(actionOnViewInWidget(1, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("John Doe"), "holderName", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("123"), "verificationCode", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, click(), "expiryDate", R.id.textinputedittext));
        
        IdlingRegistry.getInstance().unregister(idlingResource);        
        Intents.release();
    }

    private ListService getListService() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);
        return ListService.createInstance(url, auth);
    }
}
