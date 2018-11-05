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
     * Display a message to the user using a DialogFragment.
     *
     * @param message the message to be shown
     */
    void showDialog(String message);

    /**
     * Close the payment page with the given activity result
     *
     * @param activityResult the result of the activity
     * @param result containing the result code and reason of the payment
     */
    void closePage(int activityResult, PaymentResult result);
    
    /**
     * Show a message to the user using a DialogFragment, close the PaymentPage after the user has disposed the Dialog.
     * 
     * @param message to be shown to the user
     * @param activityResult activityResult for onActivityResult
     * @param paymentResult holding the payment result information
     */
    void showDialogAndClosePage(String message, int activityResult, PaymentResult paymentResult);
    
}
