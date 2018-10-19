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
import net.optile.payment.core.PaymentInputType;

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public class PaymentTheme {

    private PaymentTheme() {
    }

    /**
     * Create a new PaymentThemeBuilder, this builder can be used to build a new PaymentTheme
     *
     * @return the newly created PaymentThemeBuilder
     */
    public static PaymentThemeBuilder createPaymentThemeBuilder() {
        return new PaymentThemeBuilder();
    }

    public int getWidgetIconRes(String name) {

        if (TextUtils.isEmpty(name)) {
            return R.drawable.ic_default;
        }
        switch (name) {
            case PaymentInputType.HOLDER_NAME:
                return R.drawable.ic_name;
            case PaymentInputType.EXPIRY_DATE:
            case PaymentInputType.EXPIRY_MONTH:
            case PaymentInputType.EXPIRY_YEAR:
                return R.drawable.ic_date;
            case PaymentInputType.BANK_CODE:
            case PaymentInputType.ACCOUNT_NUMBER:
            case PaymentInputType.IBAN:
            case PaymentInputType.BIC:
                return R.drawable.ic_card;
            case PaymentInputType.VERIFICATION_CODE:
                return R.drawable.ic_lock;
            default:
                return R.drawable.ic_default;
        }
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
