/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.checkout;

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
     * Show an error to the user
     *
     * @param error to be shown to the user
     */
    void showPaymentError(String error);

    /**
     * Show that the payment was successful
     */
    void showPaymentSuccess();
}


