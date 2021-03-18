/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.redirect.RedirectRequest;

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
