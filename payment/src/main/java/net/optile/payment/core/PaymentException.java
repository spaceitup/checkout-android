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
    public final InternalError error;

    /**
     * {@inheritDoc}
     *
     * @param message the extra error info
     */
    public PaymentException(final String message) {
        super(message);
        this.error = new InternalError(message);
    }

    /**
     * {@inheritDoc}
     *
     * @param cause the cause
     */
    public PaymentException(final Throwable cause) {
        super(cause.getMessage(), cause);
        this.error = new InternalError(cause);
    }
    
    /**
     * {@inheritDoc}
     *
     * @param message the error message
     * @param cause the cause
     */
    public PaymentException(final String message, final Throwable cause) {
        super(message, cause);
        this.error = new InternalError(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @param error the internal error
     */
    public PaymentException(final InternalError error) {
        super(error.getMessage(), error.getCause());
        this.error = error;
    }
}
