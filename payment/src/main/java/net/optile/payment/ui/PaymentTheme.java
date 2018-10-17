/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui;

import android.text.TextUtils;
import net.optile.payment.R;

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme {

    public final static String INPUT_HOLDERNAME = "holderName";
    public final static String INPUT_EXPIRYMONTH = "expiryMonth";
    public final static String INPUT_EXPIRYYEAR = "expiryYear";
    public final static String INPUT_NUMBER = "number";
    public final static String INPUT_VERIFICATIONCODE = "verificationCode";
    public final static String INPUT_IBAN = "iban";
    public final static String INPUT_BIC = "bic";
    
    private PaymentTheme() {
    }

    public int getWidgetIconRes(String name) {

        if (TextUtils.isEmpty(name)) {
            return R.drawable.ic_default;
        }
        switch (name) {
        case INPUT_HOLDERNAME:
            return R.drawable.ic_name;
        case INPUT_EXPIRYMONTH:
        case INPUT_EXPIRYYEAR:
            return R.drawable.ic_date;
        case INPUT_NUMBER:
        case INPUT_IBAN:
        case INPUT_BIC:
            return R.drawable.ic_card;
        case INPUT_VERIFICATIONCODE:
            return R.drawable.ic_lock;
        default:
            return R.drawable.ic_default;
        }
    }
    
    /**
     * Create a new PaymentThemeBuilder, this builder can be used to build a new PaymentTheme
     *
     * @return the newly created PaymentThemeBuilder
     */
    public static PaymentThemeBuilder createPaymentThemeBuilder() {
        return new PaymentThemeBuilder();
    }

    /**
     * Class PaymentThemeBuilder for building PaymentTheme
     */
    public static class PaymentThemeBuilder {

        private PaymentThemeBuilder() {
        }

        public PaymentTheme build() {
            return new PaymentTheme();
        }
    }
}
