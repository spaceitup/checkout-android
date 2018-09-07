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

package net.optile.payment.network;

/**
 * The NetworkException containing the network error details
 */
public class NetworkException extends Exception {

    /**
     * The details of the network error
     */
    public final ErrorDetails details;

    /**
     * {@inheritDoc}
     *
     * @param details the details
     * @param detailMessage the detail message
     */
    public NetworkException(final ErrorDetails details, final String detailMessage) {
        super(detailMessage);
        this.details = details;
    }

    /**
     * {@inheritDoc}
     *
     * @param details the details
     * @param detailMessage the detail message
     * @param cause the cause
     */
    public NetworkException(final ErrorDetails details, final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
        this.details = details;
    }
}
