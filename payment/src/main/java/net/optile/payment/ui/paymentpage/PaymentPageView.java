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

package net.optile.payment.ui.paymentpage;

import android.content.Context;
import net.optile.payment.ui.PaymentResult;

/**
 * The PaymentPageView interface is the View part of the MVP, this is implemented by the PaymentPageActivity
 */
interface PaymentPageView {

    /**
     * Is the view currently active
     *
     * @return true when active, false otherwise
     */
    boolean isActive();

    /**
     * Get the Context from this view
     *
     * @return context
     */
    Context getContext();

    /**
     * Clear the list and clear the center message
     */
    void clear();

    /**
     * Show or hide the loading animation
     *
     * @param show if true show the loading animation, hide otherwise
     */
    void showLoading(boolean show);

    /**
     * Stop loading and show the PaymentSession
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);

    /**
     * Display a message to the user using a Dialog.
     *
     * @param message the message to be shown
     */
    void showMessage(String message);

    /**
     * Close the payment page with the given PaymentResult
     *
     * @param success indicating if the payment was successful or not
     * @param result containing the result information about the payment
     */
    void closePage(boolean success, PaymentResult result);

    /** 
     * Show a message to the user and close the payment page with the given result
     * 
     * @param message to be shown in a Dialog to the user
     * @param success indicating if the payment was successful or not
     * @param result containing the result information about the payment
     */
    void showMessageAndClosePage(String message, boolean success, PaymentResult result);


}
