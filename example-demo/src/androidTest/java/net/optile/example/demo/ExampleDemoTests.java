/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.demo;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.optile.payment.test.view.PaymentActions.actionOnViewInWidget;
import static net.optile.payment.test.view.PaymentActions.setValueInNumberPicker;
import static net.optile.payment.test.view.PaymentMatchers.isCardWithTestId;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.confirm.ConfirmActivity;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.summary.SummaryActivity;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;
import net.optile.payment.test.view.ActivityHelper;
import net.optile.payment.test.view.PaymentActions;
import net.optile.payment.ui.page.ChargePaymentActivity;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class ExampleDemoTests {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void successfulNoPresetChargeTest() throws JSONException, IOException {
        Intents.init();
        String listUrl = createListUrl(false);
        
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
        IdlingResource dialogIdlingResource = listActivity.getDialogIdlingResource();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        // Wait for the SimpleIdlingResource until the PaymentList is loaded
        IdlingRegistry.getInstance().register(loadIdlingResource);
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isDisplayed()));

        // Check and fill the payment card
        onView(list).check(matches(isCardWithTestId(0, "label_header")));
        onView(list).check(matches(isCardWithTestId(1, "card_group")));
        onView(list).perform(actionOnItemAtPosition(1, click()));
        onView(list).perform(actionOnViewInWidget(1, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("John Doe"), "holderName", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("123"), "verificationCode", R.id.textinputedittext));

        // Wait for the DialogIdlingResource until the DateDialog is visible and fill in the date
        onView(list).perform(actionOnViewInWidget(1, click(), "expiryDate", R.id.textinputedittext));
        IdlingRegistry.getInstance().register(dialogIdlingResource);
        onView(withId(R.id.text_button_neutral)).check(matches(isDisplayed()));
        onView(withId(R.id.numberpicker_year)).perform(setValueInNumberPicker(4));
        onView(withId(R.id.text_button_neutral)).perform(click());

        // Click on the widget button
        onView(list).perform(actionOnViewInWidget(1, click(), "buttonWidget", R.id.button));

        // Check that the ChargePaymentActivity is launched and wait for it to close
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
        ChargePaymentActivity chargeActivity = (ChargePaymentActivity) ActivityHelper.getCurrentActivity();
        IdlingResource chargeIdlingResource = chargeActivity.getChargeIdlingResource();
        IdlingRegistry.getInstance().register(chargeIdlingResource);

        // Check that the confirm activity is shown and that it is displayed
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(chargeIdlingResource, loadIdlingResource, dialogIdlingResource);
        Intents.release();
    }

    @Test
    public void successfulPresetChargeTest() throws IOException, JSONException {
        Intents.init();
        String listUrl = createListUrl(true);

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
        IdlingResource dialogIdlingResource = listActivity.getDialogIdlingResource();
        IdlingResource loadIdlingResource = listActivity.getLoadIdlingResource();

        // Wait for the SimpleIdlingResource until the PaymentList is loaded
        IdlingRegistry.getInstance().register(loadIdlingResource);
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isDisplayed()));

        // Check and fill the payment card
        onView(list).check(matches(isCardWithTestId(0, "label_header")));
        onView(list).check(matches(isCardWithTestId(1, "card_group")));
        onView(list).perform(actionOnItemAtPosition(1, click()));
        onView(list).perform(actionOnViewInWidget(1, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("John Doe"), "holderName", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(1, typeText("123"), "verificationCode", R.id.textinputedittext));

        // Wait for the DialogIdlingResource until the DateDialog is visible and fill in the date
        onView(list).perform(actionOnViewInWidget(1, click(), "expiryDate", R.id.textinputedittext));
        IdlingRegistry.getInstance().register(dialogIdlingResource);
        onView(withId(R.id.text_button_neutral)).check(matches(isDisplayed()));
        onView(withId(R.id.numberpicker_year)).perform(setValueInNumberPicker(4));
        onView(withId(R.id.text_button_neutral)).perform(click());

        // Click on the widget button
        IdlingResource closeIdlingResource = listActivity.getCloseIdlingResource();
        onView(list).perform(actionOnViewInWidget(1, click(), "buttonWidget", R.id.button));
        IdlingRegistry.getInstance().register(closeIdlingResource);

        // Wait for the summary activity to load and click on pay button
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());

        // Check that the ChargePaymentActivity is launched and wait for it to close
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
        ChargePaymentActivity chargeActivity = (ChargePaymentActivity) ActivityHelper.getCurrentActivity();
        IdlingResource chargeIdlingResource = chargeActivity.getChargeIdlingResource();
        IdlingRegistry.getInstance().register(chargeIdlingResource);

        // Check that the confirm activity is shown and that it is displayed
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(chargeIdlingResource, loadIdlingResource, dialogIdlingResource, closeIdlingResource);
        Intents.release();
    }

    private String createListUrl(boolean presetFirst) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);
        ListService service = ListService.createInstance(url, auth);
        ListConfig config = service.createListConfig(net.optile.example.demo.test.R.raw.listtemplate);
        config.setPresetFirst(presetFirst);
        return service.createListUrl(config);
    }
}
