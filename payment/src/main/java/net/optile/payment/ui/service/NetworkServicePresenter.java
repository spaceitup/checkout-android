/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import net.optile.payment.model.OperationResult;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;

/**
 * Presenter to be called by the NetworkService to inform about payment updates and to show i.e. a progress view or progress dialog.
 */
public interface NetworkServicePresenter {

    /** 
     * Notify the presenter that the service is in progress and requires a progress indicator 
     */
    void showProgress();
    
    /** 
     * Show a connection error dialog to the user and notify the listener when the notification is closed.
     * 
     * @param listener to be notified when the message has been closed 
     */
    void showConnErrorDialog(ThemedDialogListener listener);

    /** 
     * Show a message dialog to the user and notify the listener when the message closed.
     * 
     * @param message the message to be shown to the user
     * @param listener to be notified when the message has been closed.
     */
    void showMessageDialog(String message, ThemedDialogListener listener);
    
    /**
     * Called when the payment is prepared. The NetworkService can either pass the result through the Activity.onActivityResult or 
     * directly through this callback method.
     *
     * @param resultCode code describing the state of the paymentResult
     * @param paymentResult containing the information describing the result
     */
    void onPreparePaymentResult(int resultCode, PaymentResult paymentResult);

    /**
     * Called when the payment is processed. The NetworkService can either pass the result through the Activity.onActivityResult or 
     * directly through this callback method. 
     *
     * @param resultCode code describing the state of the paymentResult
     * @param paymentResult containing the information describing the result
     */
    void onProcessPaymentResult(int resultCode, PaymentResult paymentResult);
}
