/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.app.Activity;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The PaymentListView is the interface is the View part of the MVP, this is implemented by the PaymentListActivity
 */
interface PaymentListView {

    /**
     * Clear the payment list
     */
    void clearList();

    /**
     * Show the progress animation.
     *
     * @param show the progress when true, hide it with false
     */
    void showProgress(boolean visible);

    /**
     * Open the process payment screen to handle the operation
     *
     * @param requestCode the code identifying the request
     * @param operation to be handled by the charge payment screen
     */
    void showChargePaymentScreen(int requestCode, Operation operation);

    /**
     * Show a generic message to the user, notify the listener of events in this dialog.
     *
     * @param message the message to be shown in the dialog
     * @param listener to be notified of dialog events
     */
    void showMessageDialog(String message, ThemedDialogListener listener);

    /**
     * Show the connection error dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showConnectionDialog(ThemedDialogListener listener);

    /**
     * Stop loading and show the PaymentSession
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);

    /**
     * Pass on the ActivityResult to the activity that started this View.
     *
     * @param activityResult to be pass on
     */
    void passOnActivityResult(ActivityResult activityResult);

    /**
     * Close and return to the parent who launched the view.
     */
    void close();

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_CANCELED, PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Get the Activity of this view.
     *
     * @return the Activity of this view
     */
    Activity getActivity();

}
