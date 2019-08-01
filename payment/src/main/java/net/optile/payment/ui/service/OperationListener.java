/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import net.optile.payment.model.OperationResult;
import net.optile.payment.ui.model.PaymentSession;

/**
 * Listener to be called by the OperationService to inform about operation updates.
 */
public interface OperationListener {

    /**
     * Called when the operation was successfully posted.
     *
     * @param operationResult containing the result of the performed operation
     */
    void onOperationSuccess(OperationResult operationResult);

    /**
     * Called when an error occurred while performing the operation.
     *
     * @param cause describing the reason of failure
     */
    void onOperationError(Throwable cause);
}
