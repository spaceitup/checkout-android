/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.mrs.example.shop;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import com.payoneer.mrs.example.shop.checkout.CheckoutActivity;
import com.payoneer.mrs.example.shop.settings.SettingsActivity;
import com.payoneer.mrs.example.shop.summary.SummaryActivity;
import com.payoneer.mrs.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class GroupedCardsTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);


    @Test
    public void successfulDirectChargeTest() throws JSONException, IOException {
        Intents.init();
        int groupCardIndex = 1;

        CheckoutActivity checkoutActivity = openCheckoutActivity(false);
        IdlingResource resultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListActivity paymentListActivity = waitForPaymentListLoaded(1);
        openPaymentListCard(groupCardIndex, "card_group");
        fillPaymentListCardData(groupCardIndex);

        clickPaymentListCardButton(groupCardIndex);
        waitForConfirmActivityLoaded(resultHandledIdlingResource);
        unregister(resultHandledIdlingResource);

        Intents.release();
    }

    @Test
    public void successfulPresetChargeTest() throws IOException, JSONException {
        Intents.init();
        int groupCardIndex = 1;

        CheckoutActivity checkoutActivity = openCheckoutActivity(true);
        IdlingResource checkoutPaymentResultIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListActivity paymentListActivity = waitForPaymentListLoaded(1);
        openPaymentListCard(groupCardIndex, "card_group");
        fillPaymentListCardData(groupCardIndex);

        clickPaymentListCardButton(groupCardIndex);
        register(checkoutPaymentResultIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutPaymentResultIdlingResource);

        SummaryActivity summaryActivity = waitForSummaryActivityLoaded();
        IdlingResource summaryPaymentResultIdlingResource = summaryActivity.getResultHandledIdlingResource();
        clickSummaryPayButton();

        waitForConfirmActivityLoaded(summaryPaymentResultIdlingResource);
        unregister(summaryPaymentResultIdlingResource);
        Intents.release();
    }
}
