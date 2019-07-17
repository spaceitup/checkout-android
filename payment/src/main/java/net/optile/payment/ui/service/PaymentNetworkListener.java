/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import net.optile.payment.model.OperationResult;

/**
 * Listener to be called by the PaymentNetworkService to inform about operation request updates.
 */
public interface PaymentNetworkListener {

    /**
     * Called when the operation was successfully posted.
     *
     * @param operationResult containing the result of the performed operation
     */
    void onActivationSuccess(OperationResult operationResult);

    /**
     * Called when an error occurred while performing the operation.
     *
     * @param cause describing the reason of failure
     */
    void onActivationError(Throwable cause);
}
