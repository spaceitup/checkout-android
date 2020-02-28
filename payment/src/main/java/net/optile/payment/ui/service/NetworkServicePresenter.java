/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.Redirect;
import net.optile.payment.ui.PaymentResult;

/**
 * Presenter to be called by the NetworkService to inform about payment updates and to show i.e. a progress view or progress dialog.
 */
public interface NetworkServicePresenter {

    /**
     * Notify the presenter that the service is in progress and requires a progress indicator
     */
    void showProgress();

    /** 
     * Ask the network service to redirect the payment to an external address
     *
     * @param redirect containing the redirect data
     */
    void redirectPayment(Redirect redirect) throws PaymentException;

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
