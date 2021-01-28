/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.service;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.ui.PaymentResult;
import com.payoneer.mrs.payment.ui.redirect.RedirectRequest;

/**
 * Presenter to be called by the NetworkService to inform about payment updates and to show i.e. a progress view or progress dialog.
 */
public interface NetworkServicePresenter {

    /**
     * Notify the presenter that the service is in progress and requires a progress indicator
     *
     * @param visible true to show the progress indicator, false to hide the progress
     */
    void showProgress(boolean visible);

    /**
     * Ask the network service to redirect the payment to an external address
     *
     * @param request containing the redirect data
     */
    void redirect(RedirectRequest request) throws PaymentException;

    /**
     * Called when the payment is processed. The NetworkService can either pass the result through the Activity.onActivityResult or
     * directly through this callback method.
     *
     * @param resultCode code describing the state of the paymentResult
     * @param paymentResult containing the information describing the result
     */
    void onProcessPaymentResult(int resultCode, PaymentResult paymentResult);
}
