/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.sdk;

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
            setPaymentListTheme(R.style.CustomTheme_Toolbar).
            setChargePaymentTheme(R.style.CustomTheme_NoToolbar).
            build();
    }
}
