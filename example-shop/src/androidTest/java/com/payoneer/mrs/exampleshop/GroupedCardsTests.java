/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.mrs.exampleshop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.exampleshop.checkout.CheckoutActivity;
import com.payoneer.mrs.exampleshop.settings.SettingsActivity;
import com.payoneer.mrs.exampleshop.summary.SummaryActivity;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;
import com.payoneer.mrs.sharedtest.sdk.TestDataProvider;

import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class GroupedCardsTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testVisa_directCharge_success() {
        int groupCardIndex = 1;
        CheckoutActivity checkoutActivity = openCheckoutActivity(false);
        IdlingResource resultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(groupCardIndex, "card_group");
        PaymentListHelper.fillPaymentListCard(groupCardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(groupCardIndex);

        waitForConfirmActivityLoaded(resultHandledIdlingResource);
        unregister(resultHandledIdlingResource);
    }

    @Test
    public void testVisa_presetFlow_success() {
        int groupCardIndex = 1;
        CheckoutActivity checkoutActivity = openCheckoutActivity(true);
        IdlingResource checkoutPaymentResultIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(groupCardIndex, "card_group");
        PaymentListHelper.fillPaymentListCard(groupCardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(groupCardIndex);

        register(checkoutPaymentResultIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutPaymentResultIdlingResource);

        SummaryActivity summaryActivity = waitForSummaryActivityLoaded();
        IdlingResource summaryPaymentResultIdlingResource = summaryActivity.getResultHandledIdlingResource();
        clickSummaryPayButton();

        waitForConfirmActivityLoaded(summaryPaymentResultIdlingResource);
        unregister(summaryPaymentResultIdlingResource);
    }
}
