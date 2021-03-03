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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.payoneer.mrs.sharedtest.view.PaymentMatchers.isViewInCard;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.payoneer.mrs.exampleshop.checkout.CheckoutActivity;
import com.payoneer.mrs.exampleshop.settings.SettingsActivity;
import com.payoneer.mrs.sharedtest.sdk.PaymentListHelper;

import android.view.View;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class PresetAccountTests extends AbstractTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testPresetAccountWithoutAccountMask() {
        int presetCardIndex = 1;
        int networkCardIndex = 3;

        CheckoutActivity checkoutActivity = openCheckoutActivity(true);
        IdlingResource checkoutResultHandledIdlingResource = checkoutActivity.getResultHandledIdlingResource();
        clickCheckoutButton();

        PaymentListHelper.waitForPaymentListLoaded(1);
        PaymentListHelper.openPaymentListCard(networkCardIndex, "card_network");
        PaymentListHelper.clickPaymentListCardButton(networkCardIndex);

        register(checkoutResultHandledIdlingResource);
        waitForSummaryActivityLoaded();
        unregister(checkoutResultHandledIdlingResource);

        onView(ViewMatchers.withId(R.id.label_title)).check(matches(withText("PAYPAL")));
        clickSummaryEditButton();

        PaymentListHelper.waitForPaymentListLoaded(2);
        Matcher<View> list = withId(R.id.recyclerview_paymentlist);
        onView(list).check(matches(isViewInCard(presetCardIndex, withText("PayPal"), R.id.text_title)));
    }
}
