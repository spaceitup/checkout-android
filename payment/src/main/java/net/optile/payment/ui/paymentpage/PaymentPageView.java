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

import java.util.List;

import android.content.Context;

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
     * Show the message in the center of the payment page
     *
     * @param resId the string resource id
     */
    void showCenterMessage(int resId);

    /**
     * Hide the message in the center of the payment page
     */
    void hideCenterMessage();

    /**
     * Get the Context from this view
     *
     * @return context
     */
    Context getContext();

    /**
     * Set the list of PaymentGroups in the adapter
     *
     * @param selIndex is the index of the selected PaymentGroup
     * @param items items to be set in the view
     */
    void setItems(int selIndex, List<PaymentGroup> items);

    /**
     * Clear all items from the payment page
     */
    void clearItems();

    /**
     * Abort the payment and notify the user of this SDK
     *
     * @param code code indicating what went wrong
     * @param reason reason why the payment has been aborted
     * @param message containing the localized message to be shown to the user
     */
    void abortPayment(String code, String reason, String message);
}
