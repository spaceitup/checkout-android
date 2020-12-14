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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.optile.sharedtest.view.PaymentActions.actionOnViewInWidget;
import static net.optile.sharedtest.view.PaymentMatchers.isCardWithTestId;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;

import android.view.View;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import net.optile.example.shop.checkout.CheckoutActivity;
import net.optile.example.shop.confirm.ConfirmActivity;
import net.optile.example.shop.summary.SummaryActivity;
import net.optile.payment.ui.page.ChargePaymentActivity;
import net.optile.payment.ui.page.PaymentListActivity;
import net.optile.sharedtest.service.ListService;
import net.optile.sharedtest.view.ActivityHelper;
import net.optile.sharedtest.view.PaymentActions;

public class AbstractTest {

    public final static long CHROME_TIMEOUT = 20000;

    CheckoutActivity openCheckoutPage(boolean presetFirst) throws IOException, JSONException {
        String baseUrl = BuildConfig.paymentapi_baseurl;
        String authHeader = BuildConfig.paymentapi_authheader;
        String listUrl = ListService.createListUrl(net.optile.example.shop.test.R.raw.listtemplate, presetFirst, baseUrl, authHeader);

        onView(withId(R.id.layout_settings)).check(matches(isDisplayed()));
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

    SummaryActivity waitForSummaryPageLoaded() {
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

    void waitForConfirmPageLoaded(IdlingResource redirectIdlingResource) {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
        register(redirectIdlingResource);
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));
    }

    IdlingResource register(IdlingResource resource) {
        IdlingRegistry.getInstance().register(resource);
        return resource;
    }

    IdlingResource unregister(IdlingResource resource) {
        IdlingRegistry.getInstance().unregister(resource);
        return resource;
    }
}
