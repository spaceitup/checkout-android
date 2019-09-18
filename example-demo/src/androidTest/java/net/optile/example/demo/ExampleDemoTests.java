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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
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
        int cardIndex = 1;
        
        openPaymentList(false);
        fillGroupedNetworkCard(cardIndex);
        performDirectCharge(cardIndex);
        waitForChargeCompleted();
        Intents.release();
    }

    @Test
    public void successfulPresetChargeTest() throws IOException, JSONException {
        Intents.init();
        int cardIndex = 1;
        
        openPaymentList(true);
        fillGroupedNetworkCard(cardIndex);
        performPresetCharge(cardIndex);
        waitForChargeCompleted();
        Intents.release();
    }
    
    private void openPaymentList(boolean presetFirst) throws IOException, JSONException {
        String listUrl = createListUrl(presetFirst);
        
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

    private void fillGroupedNetworkCard(int cardIndex) {
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);

        // Check and fill the payment card
        onView(list).check(matches(isCardWithTestId(cardIndex, "card_group")));
        onView(list).perform(actionOnItemAtPosition(cardIndex, click()));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("4111111111111111"), "number", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("John Doe"), "holderName", R.id.textinputedittext));
        onView(list).perform(actionOnViewInWidget(cardIndex, typeText("123"), "verificationCode", R.id.textinputedittext));

        // Wait for the DialogIdlingResource until the DateDialog is visible and fill in the date
        IdlingResource dialogIdlingResource = listActivity.getDialogIdlingResource();
        onView(list).perform(actionOnViewInWidget(1, click(), "expiryDate", R.id.textinputedittext));
        IdlingRegistry.getInstance().register(dialogIdlingResource);

        onView(withId(R.id.text_button_neutral)).check(matches(isDisplayed()));
        onView(withId(R.id.numberpicker_year)).perform(setValueInNumberPicker(4));
        onView(withId(R.id.text_button_neutral)).perform(click());
        IdlingRegistry.getInstance().unregister(dialogIdlingResource);
    }

    private void performDirectCharge(int cardIndex) {
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));
    }

    private void performPresetCharge(int cardIndex) {
        PaymentListActivity listActivity = (PaymentListActivity) ActivityHelper.getCurrentActivity();
        IdlingResource closeIdlingResource = listActivity.getCloseIdlingResource();
        onView(withId(R.id.recyclerview_paymentlist)).perform(actionOnViewInWidget(cardIndex, click(), "buttonWidget", R.id.button));
        IdlingRegistry.getInstance().register(closeIdlingResource);

        // Wait for the summary activity to load and click on pay button
        intended(hasComponent(SummaryActivity.class.getName()));
        onView(withId(R.id.button_pay)).perform(PaymentActions.scrollToView(), click());
        IdlingRegistry.getInstance().unregister(closeIdlingResource);
    }
    
    private void waitForChargeCompleted() {
        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(withId(R.id.layout_chargepayment)).check(matches(isDisplayed()));
        ChargePaymentActivity chargeActivity = (ChargePaymentActivity) ActivityHelper.getCurrentActivity();
        IdlingResource chargeIdlingResource = chargeActivity.getChargeIdlingResource();
        IdlingRegistry.getInstance().register(chargeIdlingResource);

        // Check that the confirm activity is shown and that it is displayed
        intended(hasComponent(ConfirmActivity.class.getName()));
        onView(withId(R.id.layout_confirm)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(chargeIdlingResource);
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
