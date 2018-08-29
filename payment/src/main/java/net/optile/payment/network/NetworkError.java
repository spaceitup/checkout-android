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

import android.text.TextUtils;

/**
 * A class representing the network error
 */
public final class NetworkError {

    public enum ErrorType {

        API_ERROR("API_ERROR"),
        CONN_ERROR("CONN_ERROR"),
        INTERNAL_ERROR("INTERNAL_ERROR"),
        INVALID_VALUE("INVALID_VALUE"),
        PROTOCOL_ERROR("PROTOCOL_ERROR");
        
        private String value;

        ErrorType(String v) {
            value = v;
        }

        public String getValue() {
            return value;
        }
    }
    
    /**
     * The mandatory error type 
     */
    public ErrorType errorType;

    /**
     * The optional network status code like 400 or 500
     */
    public int statusCode;

    /**
     * The optional error message
     */
    public String message;

    /**
     * The optional exception that caused the error
     */
    public Exception cause;

    /**
     * Construct a new NetworkError
     *
     * @param errorType
     * @param statusCode
     * @param message
     * @param cause
     */
    public NetworkError(ErrorType errorType, int statusCode, String message, Exception cause) {
        this.errorType = errorType;
        this.statusCode = statusCode;
        this.message = message;
        this.cause = cause;
    }

    /**
     * Construct a new NetworkError
     *
     * @param errorType
     * @param message
     */
    public NetworkError(ErrorType errorType, String message) {
        this(errorType, 0, message, null);
    }

    /**
     * Construct a new NetworkError
     *
     * @param errorType
     * @param cause
     */
    public NetworkError(ErrorType errorType, Exception cause) {
        this(errorType, 0, null, cause);
    }

    /** 
     * Check if this error is of the given type
     * 
     * @param errorType
     * 
     * @return true when it is the same error, false otherwise
     */
    public boolean isError(ErrorType errorType) {
        return this.errorType == errorType;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("NetworkError[");
        sb.append("errorType: ");
        sb.append(this.errorType);

        if (this.statusCode != 0) {
            sb.append(", statusCode: ");
            sb.append(this.statusCode);
        }

        if (!TextUtils.isEmpty(this.message)) {
            sb.append(", message: ");
            sb.append(this.message);
        }

        if (this.cause != null) {
            sb.append(", cause: ");
            sb.append(this.cause);
        }
        sb.append("]");
    }
}
