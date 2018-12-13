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

package net.optile.payment.testapp;

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


