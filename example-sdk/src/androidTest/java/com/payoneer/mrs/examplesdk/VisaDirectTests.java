/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.examplesdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.InteractionReason;
import com.payoneer.mrs.sharedtest.sdk.TestDataProvider;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class VisaDirectTests extends AbstractTest {

    @Rule
    public ActivityTestRule<ExampleSdkActivity> rule = new ActivityTestRule<>(ExampleSdkActivity.class);

    @Test
    public void testVisa_directCharge_success() {
        IdlingResource resultIdlingResource = getResultIdlingResource();
        enterListUrl(createListUrl());
        clickActionButton();

        int groupCardIndex = 1;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(groupCardIndex, "card_group");
        PaymentListHelper.fillPaymentListCard(groupCardIndex, TestDataProvider.visaCardTestData());
        PaymentListHelper.clickPaymentListCardButton(groupCardIndex);

        register(resultIdlingResource);
        matchResultInteraction(InteractionCode.PROCEED, InteractionReason.OK);
        unregister(resultIdlingResource);
    }
}