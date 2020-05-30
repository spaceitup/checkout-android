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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.optile.payment.test.view.PaymentActions.actionOnViewInWidget;
import static net.optile.payment.test.view.PaymentActions.setValueInNumberPicker;
import static net.optile.payment.test.view.PaymentMatchers.isCardWithTestId;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.confirm.ConfirmActivity;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.summary.SummaryActivity;
import net.optile.payment.test.service.ListService;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.test.view.PaymentActions;
import net.optile.payment.ui.page.ChargePaymentActivity;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class GroupedCardsTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void successfulNoPresetChargeTest() throws JSONException, IOException {
        Intents.init();
        int cardIndex = 1;

        openPaymentList(false);
        openPaymentCard(cardIndex, "card_group");
        fillCreditCardData(cardIndex);
        clickCardButton(cardIndex);

        waitForChargeCompleted();
        Intents.release();
    }

    @Test
    public void successfulPresetChargeTest() throws IOException, JSONException {
        Intents.init();
        int cardIndex = 1;

        openPaymentList(true);
        openPaymentCard(cardIndex, "card_group");
        fillCreditCardData(cardIndex);
        performPresetCharge(cardIndex);

        waitForChargeCompleted();
        Intents.release();
    }

    private void performPresetCharge(int cardIndex) {
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource closeIdlingResource = listActivity.getCloseIdlingResource();

        clickCardButton(cardIndex);
        IdlingRegistry.getInstance().register(closeIdlingResource);
        
        // Wait for the summary activity to load and click on pay button
        intended(hasComponent(SummaryActivity.class.getName()));
        SummaryActivity summaryActivity = (SummaryActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = summaryActivity.getLoadIdlingResource();        
        IdlingRegistry.getInstance().register(loadIdlingResource);
        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());

        IdlingRegistry.getInstance().unregister(loadIdlingResource);
        IdlingRegistry.getInstance().unregister(closeIdlingResource);
    }

    private void waitForChargeCompleted() {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
        ChargePaymentActivity chargeActivity = (ChargePaymentActivity) ActivityHelper.getCurrentActivity();
        IdlingResource chargeIdlingResource = chargeActivity.getChargeIdlingResource();
        IdlingRegistry.getInstance().register(chargeIdlingResource);

        // Check that the confirm activity is shown and that it is displayed
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(chargeIdlingResource);
    }
}
