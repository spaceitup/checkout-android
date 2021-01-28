/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.core;

import com.payoneer.mrs.payment.model.ErrorInfo;

/**
 * The PaymentException containing the details of the payment error
 */
public class PaymentException extends Exception {

    /** The error info obtained from the payment API */
    private ErrorInfo errorInfo;

    /** Indicates that the exception is caused by a network failure, e.g. poor wifi connection */
    private boolean networkFailure;

    /**
     * {@inheritDoc}
     *
     * @param message the extra error info
     */
    public PaymentException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     *
     * @param cause the cause of this exception
     */
    public PaymentException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * {@inheritDoc}
     *
     * @param message containing a description of the error
     * @param cause of the error
     */
    public PaymentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new PaymentException with the cause and networkFailure flag
     *
     * @param cause of the error
     * @param networkFailure indicates that the exception was caused by a network failure
     */
    public PaymentException(final Throwable cause, boolean networkFailure) {
        super(cause);
        this.networkFailure = networkFailure;
    }

    /**
     * Constructs a new PaymentException
     *
     * @param errorInfo information about the error
     */
    public PaymentException(final ErrorInfo errorInfo) {
        super(errorInfo.getResultInfo());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public boolean getNetworkFailure() {
        return networkFailure;
    }
}
