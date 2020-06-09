/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.demo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static net.optile.payment.test.view.PaymentMatchers.isViewInCard;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import android.view.View;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.summary.SummaryActivity;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.test.view.PaymentActions;
import net.optile.payment.ui.page.ChargePaymentActivity;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PresetAccountTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPresetAccountWithoutAccountMask() throws JSONException, IOException, UiObjectNotFoundException {
        Intents.init();
        int presetCardIndex = 1;
        int networkCardIndex = 3;

        openPaymentList(true);
        openPaymentCard(networkCardIndex, "card_network");

        IdlingResource closeIdlingResource = clickCardButton(networkCardIndex);
        waitForSummaryPageLoaded();
        unregister(closeIdlingResource);

        onView(withId(R.id.label_title)).check(matches(withText("PAYPAL")));
        onView(withId(R.id.text_edit)).perform(PaymentActions.scrollToView(), click());
        waitForPaymentListLoaded(2);

        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInCard(presetCardIndex, withText("PayPal"), R.id.text_title)));
        Intents.release();
    }
}
