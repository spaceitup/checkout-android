/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.content.Context;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The PaymentListView is the interface is the View part of the MVP, this is implemented by the PaymentListActivity
 */
interface PaymentListView {

    /**
     * Clear the list
     */
    void clearList();

    /**
     * Show the ProgressView either in SEND or LOAD mode.
     *
     * @param style the style of progress animation to be used
     */
    void showProgressView(int style);

    /**
     * Stop loading and show the PaymentSession
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);

    /**
     * Show a warning message to the user
     *
     * @param message The message to be shown
     */
    void showWarningMessage(String message);

    /**
     * Close the payment page
     */
    void closePage();

    /**
     * Show a Dialog with a message about the progress (send or load)
     *
     * @param dialog to be shown
     */
    void showProgressDialog(ThemedDialogFragment dialog);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_CANCELED, PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Get the Android Context of this view.
     *
     * @return the Context of this view
     */
    Context getContext();

}
