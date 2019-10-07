/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;
import net.optile.payment.model.ErrorInfo;

/**
 * A class representing the details about the error
 */
public final class PaymentError {

    public final static String API_ERROR = "API_ERROR";
    public final static String CONN_ERROR = "CONN_ERROR";
    public final static String INTERNAL_ERROR = "INTERNAL_ERROR";
    public final static String SECURITY_ERROR = "SECURITY_ERROR";
    public final static String PROTOCOL_ERROR = "PROTOCOL_ERROR";

    /**
     * The mandatory error type
     */
    public final String errorType;
    /**
     * The optional network status code like 400 or 500
     */
    public final int statusCode;
    /**
     * The optional error data
     */
    public final String errorData;
    /**
     * The optional error info
     */
    public final ErrorInfo errorInfo;

    /**
     * Construct a new PaymentError object containing the information about the payment failure
     *
     * @param errorType the error type
     * @param errorData the error data
     */
    public PaymentError(@ErrorType final String errorType, final String errorData) {
        this(errorType, 0, errorData, null);
    }

    /**
     * Construct a new PaymentError object containing all information about a network error
     *
     * @param errorType the error type
     * @param statusCode the status code
     * @param errorData the error data
     * @param errorInfo the error info
     */
    public PaymentError(@ErrorType final String errorType, final int statusCode, final String errorData,
        final ErrorInfo errorInfo) {
        this.errorType = errorType;
        this.statusCode = statusCode;
        this.errorData = errorData;
        this.errorInfo = errorInfo;
    }

    /**
     * Check if this error is of the given type
     *
     * @param errorType the error type
     * @return true when it is the same error, false otherwise
     */
    public boolean isError(@ErrorType final String errorType) {
        return this.errorType.equals(errorType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("PaymentError[");
        sb.append("errorType: ");
        sb.append(this.errorType);

        if (this.statusCode != 0) {
            sb.append(", statusCode: ");
            sb.append(this.statusCode);
        }
        if (this.errorData != null) {
            sb.append(", errorData: ");
            sb.append(this.errorData);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * The interface Error type.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        API_ERROR,
        CONN_ERROR,
        INTERNAL_ERROR,
        SECURITY_ERROR,
        PROTOCOL_ERROR
    })
    public @interface ErrorType {
    }
}
