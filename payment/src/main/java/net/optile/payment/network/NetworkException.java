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
     */
    public NetworkException(final ErrorDetails details, final String detailMessage) {
        super(detailMessage);
        this.details = details;
    }
    
    /**
     * {@inheritDoc}
     */
    public NetworkException(final ErrorDetails details, final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
        this.details = details;
    }
}
