/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.shop.shared;

/**
 * Generic ShopException
 */
public class ShopException extends Exception {

    /**
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     */
    public ShopException(final String detailMessage) {
        super(detailMessage);
    }

    /**
     * {@inheritDoc}
     *
     * @param detailMessage the detail message
     * @param cause the cause
     */
    public ShopException(final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
    }
}
