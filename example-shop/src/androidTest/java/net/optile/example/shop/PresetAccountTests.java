/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.shop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static net.optile.sharedtest.view.PaymentMatchers.isViewInCard;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiObjectNotFoundException;
import net.optile.example.shop.checkout.CheckoutActivity;
import net.optile.example.shop.settings.SettingsActivity;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PresetAccountTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPresetAccountWithoutAccountMask() throws JSONException, IOException, UiObjectNotFoundException {
        Intents.init();

        int presetCardIndex = 1;
        int networkCardIndex = 3;

        CheckoutActivity checkoutActivity = openCheckoutPage(true);
        IdlingResource checkoutResultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListActivity paymentListActivity = waitForPaymentListLoaded(1);
        openPaymentListCard(networkCardIndex, "card_network");
        clickPaymentListCardButton(networkCardIndex);
        register(checkoutResultHandledIdlingResource);

        waitForSummaryPageLoaded();
        unregister(checkoutResultHandledIdlingResource);

        onView(withId(R.id.label_title)).check(matches(withText("PAYPAL")));
        clickSummaryEditButton();

        waitForPaymentListLoaded(2);
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInCard(presetCardIndex, withText("PayPal"), R.id.text_title)));

        Intents.release();
    }
}
