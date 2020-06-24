/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.app.Activity;
import net.optile.payment.model.Interaction;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;

/**
 * The BasePaymentView interface is the View part of the MVP
 */
interface BasePaymentView {

    /**
     * Show the progress animation.
     *
     * @param visible show the progress when true, false otherwise
     */
    void showProgress(boolean visible);

    /**
     * Show the default error dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showDefaultErrorDialog(ThemedDialogListener listener);

    /**
     * Show the connection error dialog to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showConnectionErrorDialog(ThemedDialogListener listener);

    /**
     * Show the interaction text to the user, notify the listener of events in this dialog.
     *
     * @param listener to be notified of dialog events
     */
    void showInteractionDialog(Interaction interaction, ThemedDialogListener listener);

    /**
     * Show a warning message to the user
     *
     * @param message The message to be shown
     */
    void showWarningMessage(String message);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_CANCELED
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Pass on the ActivityResult to the activity that started this View.
     *
     * @param activityResult to be pass on
     */
    void passOnActivityResult(ActivityResult activityResult);

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
