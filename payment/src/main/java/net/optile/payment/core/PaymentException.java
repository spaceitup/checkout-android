/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

/**
 * The PaymentException containing the payment error details
 */
public class PaymentException extends Exception {

    /**
     * The details of the error
     */
    public final PaymentError error;

    /**
     * {@inheritDoc}
     *
     * @param message the extra error info
     */
    public PaymentException(final String message) {
        super(message);
        this.error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
    }

    /**
     * {@inheritDoc}
     *
     * @param message the extra error info
     * @param cause the cause
     */
    public PaymentException(final String message, final Throwable cause) {
        super(message, cause);
        this.error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
    }
    
    /**
     * {@inheritDoc}
     *
     * @param error the error
     * @param message the extra error info
     */
    public PaymentException(final PaymentError error, final String message) {
        super(message);
        this.error = error;
    }

    /**
     * {@inheritDoc}
     *
     * @param error the error
     * @param message the extra error info
     * @param cause the cause
     */
    public PaymentException(final PaymentError error, final String message, final Throwable cause) {
        super(message, cause);
        this.error = error;
    }
}
