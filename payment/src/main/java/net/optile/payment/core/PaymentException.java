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
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     */
    public PaymentException(final String detailMessage) {
        super(detailMessage);
    }

    /**
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     * @param cause the cause
     */
    public PaymentException(final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
    }
}
