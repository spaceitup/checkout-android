/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

import android.content.Context;

/**
 * The interface Checkout view.
 */
interface CheckoutView {

    /**
     * Open the PaymentPage for the provided listUrl
     *
     * @param listUrl the current listUrl
     */
    void openPaymentPage(String listUrl);

    /**
     * Show that the payment was successful, this will either show the confirm or summary screen
     */
    void showPaymentSuccess();

    /**
     * Show an error to the user
     *
     * @param error to be shown to the user
     */
    void showPaymentError(String error);

    /**
     * Close the payment and return to the SettingsScreen
     */
    void closePayment();

    /**
     * Get the Context in which this View is running
     *
     * @return Context of this View
     */
    Context getContext();
}
 

