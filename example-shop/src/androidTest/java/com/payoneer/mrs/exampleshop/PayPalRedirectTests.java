/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.mrs.exampleshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.exampleshop.checkout.CheckoutActivity;
import com.payoneer.mrs.exampleshop.settings.SettingsActivity;
import com.payoneer.mrs.exampleshop.summary.SummaryActivity;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;
import com.payoneer.mrs.sharedtest.sdk.CardDataProvider;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;
import com.payoneer.mrs.sharedtest.view.UiDeviceHelper;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PayPalRedirectTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPayPalRedirect_directCharge_customerAccept() {
        int networkCardIndex = 3;
        CheckoutActivity checkoutActivity = openCheckoutActivity(false);
        IdlingResource resultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);
        clickDecisionPageButton("customer-accept");

        waitForConfirmActivityLoaded(resultHandledIdlingResource);
        unregister(resultHandledIdlingResource);
    }

    @Test
    public void testPayPalRedirect_presetFlow_customerAccept() {
        int networkCardIndex = 3;
        CheckoutActivity checkoutActivity = openCheckoutActivity(true);
        IdlingResource checkoutPaymentResultIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        register(checkoutPaymentResultIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutPaymentResultIdlingResource);

        SummaryActivity summaryActivity = waitForSummaryActivityLoaded();
        IdlingResource summaryPaymentResultIdlingResource = summaryActivity.getResultHandledIdlingResource();
        clickSummaryPayButton();
        clickDecisionPageButton("customer-accept");

        waitForConfirmActivityLoaded(summaryPaymentResultIdlingResource);
        unregister(summaryPaymentResultIdlingResource);
    }

    private void clickDecisionPageButton(String buttonId) {
        UiDeviceHelper.checkUiObjectContainsText("customer decision page");
        UiDeviceHelper.clickUiObjectByResourceName(buttonId);
        UiDeviceHelper.waitUiObjectHasPackage("com.payoneer.mrs.exampleshop");
    }
}
