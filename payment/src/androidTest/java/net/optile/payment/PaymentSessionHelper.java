/**
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import net.optile.payment.localization.Localization;
import net.optile.payment.test.service.ListService;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.service.LocalizationService;


/**
 * Class with helper methods to setup a PaymentSession in the Android SDK
 */
public class PaymentSessionHelper {

    /** 
     * Setup the PaymentUI so it can be used by the internal screens in the Android SDK
     * 
     * @param jsonResId resource id of the json list body
     * @param presetFirst set presetFirst to true or false in the list body
     */
    public static void setupPaymentUI(int jsonResId, boolean presetFirst) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String listUrl = ListService.createListUrl(jsonResId, presetFirst);

        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);

        paymentUI.setPaymentTheme(PaymentTheme.createDefault());
        paymentUI.setValidationResId(R.raw.validations);
        paymentUI.setGroupResId(R.raw.groups);

        Localization loc = Localization.getInstance();
        loc.setLocalizations(LocalizationService.createLocalLocalization(context), null);
    }
}
