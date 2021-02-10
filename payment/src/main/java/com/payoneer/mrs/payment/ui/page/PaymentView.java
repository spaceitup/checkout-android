/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.page;

import android.app.Activity;

import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.ui.PaymentActivityResult;
import com.payoneer.mrs.payment.ui.PaymentResult;
import com.payoneer.mrs.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;

/**
 * The view (MVP) interface for screens that handle payments
 */
interface PaymentView {

    /**
     * Show the progress animation.
     *
     * @param visible show the progress when true, false otherwise
     */
    void showProgress(boolean visible);

    /**
     * Show the connection error dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showConnectionErrorDialog(PaymentDialogListener listener);

    /**
     * Show the interaction text to the user, notify the listener of events in this dialog.
     * When there is no localization for the interaction then the default error will be shown to the user.
     *
     * @param listener to be notified of dialog events
     */
    void showInteractionDialog(Interaction interaction, PaymentDialogListener listener);

    /**
     * Show a warning message to the user
     *
     * @param message The message to be shown
     */
    void showWarningMessage(String message);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Pass on the ActivityResult to the activity that started this View.
     *
     * @param paymentActivityResult to be pass on
     */
    void passOnActivityResult(PaymentActivityResult paymentActivityResult);

    /**
     * Get the Android Context of this view.
     *
     * @return the Context of this view
     */
    Activity getActivity();

    /**
     * Close this payment view
     */
    void close();
}
