/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.sharedtest.service;

/**
 * Exception containing the details when an error happened creating a new list
 */
public class ListServiceException extends RuntimeException {

    public ListServiceException(String message) {
        super(message);
    }

    public ListServiceException(Throwable cause) {
        super(cause);
    }

    public ListServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
