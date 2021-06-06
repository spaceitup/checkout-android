/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.OperationResult;

/**
 * Interface for network services, a NetworkService is responsible for activating and
 * processing a payment through the supported payment network.
 */
public abstract class NetworkService {

    protected NetworkServiceListener listener;

    /**
     * Stop this NetworkService
     */
    public void stop() {
    }

    /**
     * Set the listener in this NetworkService
     *
     * @param listener the listener to be set
     */
    public void setListener(NetworkServiceListener listener) {
        this.listener = listener;
    }

    /**
     * Process the payment through this NetworkService.
     *
     * @param operation that should be processed
     */
    public void processPayment(Operation operation) {
    }

    /**
     * Delete the AccountRegistration through this NetworkService.
     *
     * @param accountRegistration to be deleted from this network
     */
    public void deleteAccount(AccountRegistration accountRegistration) {
    }

    /**
     * Notify the network service that the payment has been redirected and an OperationResult has been received.
     *
     * @param result containing the result of the operation
     */
    public void onRedirectSuccess(OperationResult result) {
    }

    /**
     * Notify the network service that the redirect has failed to receive an OperationResult.
     * The network service should handle this situation and make sure the NetworkServiceListener is notified with the
     * appropriate PaymentResult.
     */
    public void onRedirectError() {
    }
}
