/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.examplesdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.InteractionReason;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;
import com.payoneer.mrs.payment.ui.page.PaymentListActivity;
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
    public ActivityTestRule<ExampleSdkActivity> rule = new ActivityTestRule<>(ExampleSdkActivity.class);

    @Test
    public void testPayPalRedirect_directCharge_browserClosed() {
        IdlingResource resultIdlingResource = getResultIdlingResource();
        enterListUrl(createListUrl());
        clickActionButton();

        int networkCardIndex = 3;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        clickDecisionPageButton("com.android.chrome:id/close_button");
        register(resultIdlingResource);
        onView(ViewMatchers.withId(R.id.text_interactioncode)).check(matches(withText(InteractionCode.VERIFY)));
        onView(ViewMatchers.withId(R.id.text_interactionreason)).check(matches(withText(InteractionReason.CLIENTSIDE_ERROR)));
        unregister(resultIdlingResource);
    }

    @Test
    public void testPayPalRedirect_directCharge_customerAccept() {
        IdlingResource resultIdlingResource = getResultIdlingResource();
        enterListUrl(createListUrl());
        clickActionButton();

        int networkCardIndex = 3;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        clickDecisionPageButton("customer-accept");
        register(resultIdlingResource);
        onView(ViewMatchers.withId(R.id.text_interactioncode)).check(matches(withText(InteractionCode.PROCEED)));
        onView(ViewMatchers.withId(R.id.text_interactionreason)).check(matches(withText(InteractionReason.OK)));
        unregister(resultIdlingResource);
    }

    @Test
    public void testPayPalRedirect_directCharge_customerAbort() {
        enterListUrl(createListUrl());
        clickActionButton();

        int networkCardIndex = 3;
        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        clickDecisionPageButton("customer-abort");
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(ViewMatchers.withId(R.id.alertTitle)).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        intended(hasComponent(PaymentListActivity.class.getName()));
    }

    private void clickDecisionPageButton(String buttonId) {
        UiDeviceHelper.checkUiObjectContainsText("customer decision page");
        UiDeviceHelper.clickUiObjectByResourceName(buttonId);
        UiDeviceHelper.waitUiObjectHasPackage("com.payoneer.mrs.examplesdk");
    }
}
