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

package net.optile.payment.network;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;
import net.optile.payment.model.ErrorInfo;

/**
 * A class representing the details about the error
 */
public final class ErrorDetails {

    /**
     * The constant API_ERROR.
     */
    public final static String API_ERROR = "API_ERROR";
    /**
     * The constant CONN_ERROR.
     */
    public final static String CONN_ERROR = "CONN_ERROR";
    /**
     * The constant INTERNAL_ERROR.
     */
    public final static String INTERNAL_ERROR = "INTERNAL_ERROR";
    /**
     * The constant SECURITY_ERROR.
     */
    public final static String SECURITY_ERROR = "SECURITY_ERROR";
    /**
     * The constant PROTOCOL_ERROR.
     */
    public final static String PROTOCOL_ERROR = "PROTOCOL_ERROR";
    /**
     * The mandatory error type
     */
    public final String errorType;
    /**
     * The source of the error
     */
    public final String source;
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
     * Construct a new ErrorDetails object containing all information about a network error
     *
     * @param source the source
     * @param errorType the error type
     * @param statusCode the status code
     * @param errorData the error data
     * @param errorInfo the error info
     */
    public ErrorDetails(final String source, @ErrorType final String errorType, final int statusCode, final String errorData, final ErrorInfo errorInfo) {
        this.source = source;
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
        sb.append("ErrorDetails[");
        sb.append("errorType: ");
        sb.append(this.errorType);

        if (this.source != null) {
            sb.append(", source: ");
            sb.append(this.source);
        }
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
