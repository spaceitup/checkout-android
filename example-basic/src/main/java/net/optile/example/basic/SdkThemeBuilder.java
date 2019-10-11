/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.basic;

import net.optile.payment.ui.PaymentTheme;

/**
 * Class used to create a custom PaymentTheme for theming the Android SDK Payment page
 */
final class SdkThemeBuilder {

    /**
     * Create a default payment theme
     *
     * @return the default theme
     */
    public static PaymentTheme createDefaultTheme() {
        return PaymentTheme.createDefault();
    }

    /**
     * Construct a new PaymentTheme with the custom orange skin
     *
     * @return PaymentTheme containing the custom skin
     */
    public static PaymentTheme createCustomTheme() {
        return PaymentTheme.createBuilder().
            setPaymentListTheme(R.style.CustomPaymentTheme_PaymentList).
            setChargePaymentTheme(R.style.CustomPaymentTheme_ChargePayment).
            setDateDialogTheme(R.style.CustomDialogTheme_Date).
            setMessageDialogTheme(R.style.CustomDialogTheme_Message).
            setValidationColorOk(R.color.custom_validationok).
            setValidationColorUnknown(R.color.custom_validationunknown).
            setValidationColorError(R.color.custom_validationerror).
            setDefaultIconMapping().
            build();
    }
}
