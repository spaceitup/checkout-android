/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

/**
 * The interface Checkout view.
 */
interface CheckoutView {

    /**
     * Show the summary page to the user
     */
    void showPaymentSummary();

    /**
     * Show the payment confirmation to the user
     */
    void showPaymentConfirmation();

    /**
     * Stop the payment and show error message to the user
     */
    void stopPaymentWithErrorMessage();
}
 

