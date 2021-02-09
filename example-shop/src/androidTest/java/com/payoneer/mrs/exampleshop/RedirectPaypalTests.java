/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.mrs.exampleshop;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.payoneer.mrs.exampleshop.settings.SettingsActivity;
import com.payoneer.mrs.payment.ui.page.ChargePaymentActivity;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class RedirectPaypalTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPaypalSuccess() throws JSONException, IOException {
        Intents.init();
        int networkCardIndex = 3;

        openCheckoutActivity(false);
        clickCheckoutButton();

        waitForPaymentListLoaded(1);
        openPaymentListCard(networkCardIndex, "card_network");
        clickPaymentListCardButton(networkCardIndex);

        checkPayPalChromeDisplayed();
        closeChromeBrowser();
        Intents.release();
    }

    @Test
    public void testPaypalBrowserClosed() throws JSONException, IOException {
        Intents.init();
        int networkCardIndex = 3;

        openCheckoutActivity(false);
        clickCheckoutButton();

        waitForPaymentListLoaded(1);
        openPaymentListCard(networkCardIndex, "card_network");
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

    private void checkPayPalChromeDisplayed() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.res("com.android.chrome:id/url_bar")), CHROME_TIMEOUT);
        String url = "sandbox.paypal.com";
        uiObject.wait(Until.textContains(url), CHROME_TIMEOUT);
        assertThat(uiObject.getText(), containsString(url));
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
