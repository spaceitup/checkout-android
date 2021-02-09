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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.payoneer.mrs.sharedtest.view.PaymentActions.actionOnViewInWidget;
import static com.payoneer.mrs.sharedtest.view.PaymentMatchers.isCardWithTestId;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;

import com.payoneer.mrs.exampleshop.BuildConfig;
import com.payoneer.mrs.exampleshop.R;
import com.payoneer.mrs.exampleshop.checkout.CheckoutActivity;
import com.payoneer.mrs.exampleshop.confirm.ConfirmActivity;
import com.payoneer.mrs.exampleshop.summary.SummaryActivity;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;
import com.payoneer.mrs.payment.ui.page.PaymentListActivity;
import com.payoneer.mrs.sharedtest.service.ListService;
import com.payoneer.mrs.sharedtest.view.ActivityHelper;
import com.payoneer.mrs.sharedtest.view.PaymentActions;

import android.view.View;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;

public class AbstractTest {

    public final static long CHROME_TIMEOUT = 20000;

    CheckoutActivity openCheckoutActivity(boolean presetFirst) throws IOException, JSONException {
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

    PaymentListActivity waitForPaymentListLoaded(int count) {
        intended(hasComponent(PaymentListActivity.class.getName()), times(count));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        register(loadIdlingResource);
        onView(withId(R.id.recyclerview_paymentlist)).check(matches(isDisplayed()));
        unregister(loadIdlingResource);
        return listActivity;
    }

    void openPaymentListCard(int cardIndex, String cardTestId) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isCardWithTestId(cardIndex, cardTestId)));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));
    }

    void fillPaymentListCardData(int cardIndex) {
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list)
            .perform(actionOnViewInWidget(cardIndex, typeText("4111111111111111"), "number", R.id.textinputedittext), closeSoftKeyboard());
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("1245"), "expiryDate", R.id.textinputedittext), closeSoftKeyboard());
        onView(list)
            .perform(actionOnViewInWidget(cardIndex, typeText("123"), "verificationCode", R.id.textinputedittext), closeSoftKeyboard());
        onView(list)
            .perform(actionOnViewInWidget(cardIndex, typeText("Thomas Smith"), "holderName", R.id.textinputedittext), closeSoftKeyboard());
    }

    void clickPaymentListCardButton(int cardIndex) {
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));
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

    ConfirmActivity waitForConfirmActivityLoaded(IdlingResource resultHandledIdlingResource) {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
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
