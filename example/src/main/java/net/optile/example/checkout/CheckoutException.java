/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example.checkout;

/**
 * General CheckoutException
 */
public class CheckoutException extends Exception {

    /**
     * {@inheritDoc}
     */
    public CheckoutException() {
    }

    /**
     * {@inheritDoc}
     */
    public CheckoutException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * {@inheritDoc}
     */
    public CheckoutException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }
}
