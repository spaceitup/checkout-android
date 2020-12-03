/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.shop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import net.optile.example.shop.settings.SettingsActivity;
import net.optile.sharedtest.view.PaymentActions;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class GroupedCardsTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void successfulDirectChargeTest() throws JSONException, IOException {
        Intents.init();
        int groupCardIndex = 1;

        openPaymentList(false);
        openPaymentCard(groupCardIndex, "card_group");
        fillCreditCardData(groupCardIndex);

        IdlingResource closeIdlingResource = clickCardButton(groupCardIndex);
        waitForChargeCompleted();
        unregister(closeIdlingResource);
        Intents.release();
    }

    @Test
    public void successfulPresetChargeTest() throws IOException, JSONException {
        Intents.init();
        int groupCardIndex = 1;

        openPaymentList(true);
        openPaymentCard(groupCardIndex, "card_group");
        fillCreditCardData(groupCardIndex);

        IdlingResource closeIdlingResource = clickCardButton(groupCardIndex);
        waitForSummaryPageLoaded();
        unregister(closeIdlingResource);

        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());
        waitForChargeCompleted();
        Intents.release();
    }
}
