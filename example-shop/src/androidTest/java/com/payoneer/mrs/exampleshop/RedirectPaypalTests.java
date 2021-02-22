/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.mrs.exampleshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.exampleshop.settings.SettingsActivity;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class RedirectPaypalTests extends AbstractTest {

    @Rule
    public ActivityScenarioRule<SettingsActivity> settingsActivityRule = new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void testPaypalSuccess() throws JSONException, IOException {
        Intents.init();
        int networkCardIndex = 3;

        openCheckoutActivity(false);
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        clickPaymentListCardButton(networkCardIndex);

        checkCustomerDecisionPageDisplayed();
        closeChromeBrowser();
        Intents.release();
    }

    @Test
    public void testPaypalBrowserClosed() throws JSONException, IOException {
        Intents.init();
        int networkCardIndex = 3;

        openCheckoutActivity(false);
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        clickPaymentListCardButton(networkCardIndex);
        closeChromeBrowser();

        intended(hasComponent(ChargePaymentActivity.class.getName()));
        onView(ViewMatchers.withId(R.id.alertTitle)).check(matches(isDisplayed()));
        Intents.release();
    }

    private void closeChromeBrowser() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.res("com.android.chrome:id/close_button")), CHROME_TIMEOUT);
        uiObject.wait(Until.enabled(true), CHROME_TIMEOUT);
        uiObject.click();
    }

    private void checkCustomerDecisionPageDisplayed() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        String label = "customer decision page";
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.text(label)), CHROME_TIMEOUT);        
        assertThat(uiObject.getText(), containsString(label));
    }

    void clickUiObjectByResource(UiDevice uiDevice, String resourceName) {
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.res(resourceName)), CHROME_TIMEOUT);
        uiObject.wait(Until.enabled(true), CHROME_TIMEOUT);
        uiObject.click();
    }

    private void waitForAppRestarted(UiDevice uiDevice) {
        uiDevice.wait(Until.hasObject(By.pkg("com.payoneer.mrs.example.shop")), CHROME_TIMEOUT);
    }
}
