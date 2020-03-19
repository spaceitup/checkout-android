/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static net.optile.payment.test.view.PaymentActions.actionOnViewInWidget;
import static net.optile.payment.test.view.PaymentActions.setValueInNumberPicker;
import static net.optile.payment.test.view.PaymentMatchers.hasTextInputLayoutError;
import static net.optile.payment.test.view.PaymentMatchers.hasTextInputLayoutHint;
import static net.optile.payment.test.view.PaymentMatchers.isCardWithTestId;
import static net.optile.payment.test.view.PaymentMatchers.isViewInCard;
import static net.optile.payment.test.view.PaymentMatchers.isViewInWidget;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.view.View;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreditCardTests {
    @Rule
    public ActivityTestRule<PaymentListActivity> activityRule = new ActivityTestRule<>(PaymentListActivity.class, true, false);

    @Test
    public void missingCreditCardDetailsTest() throws JSONException, IOException {
        Intents.init();

        // open the payment list
        openPaymentList();

        int cardIndex = 3;
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isCardWithTestId(cardIndex, "card_group")));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));
        onView(list).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));

        onView(list).check(matches(isViewInWidget(cardIndex, hasTextInputLayoutHint("Card Number"), "number", R.id.textinputlayout)));
        onView(list)
            .check(matches(isViewInWidget(cardIndex, hasTextInputLayoutError("Missing card number"), "number", R.id.textinputlayout)));

        onView(list).check(matches(isViewInWidget(cardIndex, hasTextInputLayoutHint("Holder Name"), "holderName", R.id.textinputlayout)));
        onView(list)
            .check(matches(isViewInWidget(cardIndex, hasTextInputLayoutError("Missing holder name"), "holderName", R.id.textinputlayout)));

        onView(list).check(
            matches(isViewInWidget(cardIndex, hasTextInputLayoutHint("Valid Thru Month / Year"), "expiryDate", R.id.textinputlayout)));
        onView(list)
            .check(matches(isViewInWidget(cardIndex, hasTextInputLayoutError("Missing expiry date"), "expiryDate", R.id.textinputlayout)));

        onView(list)
            .check(matches(isViewInWidget(cardIndex, hasTextInputLayoutHint("Security Code"), "verificationCode", R.id.textinputlayout)));
        onView(list).check(matches(
            isViewInWidget(cardIndex, hasTextInputLayoutError("Missing verification code"), "verificationCode", R.id.textinputlayout)));

        Intents.release();
    }

    @Test
    public void validVisaPaymentTest() throws IOException, JSONException, InterruptedException {
        Intents.init();

        openPaymentList();
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();

        int cardIndex = 3;
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isCardWithTestId(cardIndex, "card_group")));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));

        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).check(matches(isViewInCard(cardIndex, withText("Visa"), R.id.title1)));

        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("John Doe"), "holderName", R.id.textinputedittext));

        // Wait for the DialogIdlingResource until the DateDialog is visible and fill in the date
        IdlingResource dialogIdlingResource = listActivity.getDialogIdlingResource();
        onView(list).perform(actionOnViewInWidget(3, click(), "expiryDate", R.id.textinputedittext));
        IdlingRegistry.getInstance().register(dialogIdlingResource);


        onView(withId(R.id.dialogbutton_neutral)).check(matches(isDisplayed()));
        onView(withId(R.id.numberpicker_year)).perform(setValueInNumberPicker(4));
        onView(withId(R.id.dialogbutton_neutral)).perform(click());
        IdlingRegistry.getInstance().unregister(dialogIdlingResource);

        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("123"), "verificationCode", R.id.textinputedittext));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));

        Intents.release();

    }

    @Test
    public void payWithSavedCreditCard() throws JSONException, IOException {
        Intents.init();

        openPaymentList();

        int cardIndex = 1;
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isCardWithTestId(cardIndex, "card_savedaccount")));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));

        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("123"), "verificationCode", R.id.textinputedittext));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));

        Intents.release();

    }


    private void openPaymentList() throws JSONException, IOException {
        PaymentSessionHelper.setupPaymentUI(net.optile.payment.test.R.raw.listtemplate_register, false);

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        activityRule.launchActivity(PaymentListActivity.createStartIntent(context));

        intended(hasComponent(PaymentListActivity.class.getName()));
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        IdlingRegistry.getInstance().register(loadIdlingResource);
        onView(withId(R.id.recyclerview_paymentlist)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(loadIdlingResource);
    }
}
