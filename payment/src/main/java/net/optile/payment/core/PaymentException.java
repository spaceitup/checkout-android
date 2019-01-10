/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
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
