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

package net.optile.payment.ui.page;

import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.model.PaymentSession;

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
     * Get the string resource given the resource id
     *
     * @return the string resource
     */
    String getStringRes(int resId);

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
     * Display a message to the user using a Dialog
     *
     * @param message the message to be shown
     */
    void showMessage(String message);

    /**
     * Close the payment page
     */
    void closePage();

    /**
     * First show the message to the user and then close the Payment page
     *
     * @param message to be shown in a Dialog to the user
     */
    void closePageWithMessage(String message);

    /**
     * Set the current activity payment result
     *
     * @param success true when the charge request was successful, false otherwise
     * @param result containing the Payment result state
     */
    void setPaymentResult(boolean success, PaymentResult result);
}
