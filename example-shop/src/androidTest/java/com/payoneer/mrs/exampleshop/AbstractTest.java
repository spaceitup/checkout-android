/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.exampleshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.payoneer.mrs.exampleshop.checkout.CheckoutActivity;
import com.payoneer.mrs.exampleshop.confirm.ConfirmActivity;
import com.payoneer.mrs.exampleshop.summary.SummaryActivity;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;
import com.payoneer.mrs.sharedtest.sdk.TestDataProvider;
import com.payoneer.mrs.sharedtest.service.ListService;
import com.payoneer.mrs.sharedtest.view.ActivityHelper;
import com.payoneer.mrs.sharedtest.view.PaymentActions;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

class AbstractTest {

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }

    CheckoutActivity openCheckoutActivity(boolean presetFirst) {
        String baseUrl = BuildConfig.paymentapi_baseurl;
        String authHeader = BuildConfig.paymentapi_authheader;
        String listUrl = ListService.createListUrl(com.payoneer.mrs.exampleshop.test.R.raw.listtemplate, presetFirst, baseUrl, authHeader);

        onView(ViewMatchers.withId(R.id.layout_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_settings)).perform(click());
        intended(hasComponent(CheckoutActivity.class.getName()));
        return (CheckoutActivity) ActivityHelper.getCurrentActivity();
    }

    void clickCheckoutButton() {
        intended(hasComponent(CheckoutActivity.class.getName()));
        onView(withId(R.id.button_checkout)).perform(PaymentActions.scrollToView(), click());
    }

    void fillPaymentListCardData(int cardIndex) {
        Map<String, String> cardData = TestDataProvider.visaCardTestData();
        PaymentListHelper.fillPaymentListCard(cardIndex, cardData);
    }

    SummaryActivity waitForSummaryActivityLoaded() {
        intended(hasComponent(SummaryActivity.class.getName()));
        SummaryActivity summaryActivity = (SummaryActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = summaryActivity.getLoadIdlingResource();
        register(loadIdlingResource);
        onView(withId(R.id.layout_coordinator)).check(matches(isDisplayed()));
        unregister(loadIdlingResource);
        return summaryActivity;
    }

    void clickSummaryPayButton() {
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());
    }

    void clickSummaryEditButton() {
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_edit)).perform(PaymentActions.scrollToView(), click());
    }

    void waitForChargePaymentActivityDisplayed() {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
    }

    ConfirmActivity waitForConfirmActivityLoaded(IdlingResource resultHandledIdlingResource) {
        register(resultHandledIdlingResource);
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));
        return (ConfirmActivity) ActivityHelper.getCurrentActivity();
    }

    void register(IdlingResource resource) {
        IdlingRegistry.getInstance().register(resource);
    }

    void unregister(IdlingResource resource) {
        IdlingRegistry.getInstance().unregister(resource);
    }
}
