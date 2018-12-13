/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.testapp;

/**
 * General CheckoutException
 */
class CheckoutException extends Exception {

    /**
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     */
    public CheckoutException(final String detailMessage) {
        super(detailMessage);
    }

    /**
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     * @param cause the cause
     */
    public CheckoutException(final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
    }
}
