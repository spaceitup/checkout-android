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

import android.view.View;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.payment.test.service.ListService;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.test.view.PaymentActions;
import net.optile.payment.ui.page.PaymentListActivity;

public class AbstractTest {

    public final static long CHROME_TIMEOUT = 20000;

    void openPaymentList(boolean presetFirst) throws IOException, JSONException {
        String listUrl = ListService.createListUrl(net.optile.example.demo.test.R.raw.listtemplate, presetFirst);

        // enter the listUrl in the settings screen and click the button
        onView(withId(R.id.layout_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_settings)).perform(click());

        // Wait for CheckoutActivity to be visible and click the pay button
        intended(hasComponent(CheckoutActivity.class.getName()));
        onView(withId(R.id.button_checkout)).perform(PaymentActions.scrollToView(), click());

        // Obtain the PaymentListActivity 
        intended(hasComponent(PaymentListActivity.class.getName()));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        // Wait for the loadIdlingResource until the PaymentList is loaded
        IdlingRegistry.getInstance().register(loadIdlingResource);
        onView(withId(R.id.recyclerview_paymentlist)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(loadIdlingResource);
    }

    void openPaymentCard(int cardIndex, String cardTestId) {
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);

        onView(list).check(matches(isCardWithTestId(cardIndex, cardTestId)));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));
    }
    
    void fillCreditCardData(int cardIndex) {
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);

        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("John Doe"), "holderName", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("1245"), "expiryDate", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("123"), "verificationCode", R.id.textinputedittext));
        Espresso.closeSoftKeyboard();
    }
    
    void clickCardButton(int cardIndex) {
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));
    }
}
