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
     * Show or hide the loading animation
     *
     * @param show if true show the loading animation, false hides the loading animation
     */
    void showLoading(boolean show);

    /**
     * Get the Context from this view
     *
     * @return context
     */
    Context getContext();

    /**
     * Show the PaymentSession to the user
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);

    /**
     * Clear the view from any previously shown PaymentSessions
     */
    void clear();

    /** 
     * Show the error to the user
     * 
     * @param resId the resource string id
     */
    void showError(int resId);
    
    /**
     * Abort the payment and notify the user of this SDK
     *
     * @param success indicating if the payment request was successfull
     * @param result containing the result code and reason of the payment
     */
    void closePaymentPage(boolean success, PaymentResult result);
}
