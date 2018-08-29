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
        INVALID_ARGUMENT("INVALID_ARGUMENT"),
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
     * The mandatory error 
     */
    public ErrorType error;

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
     * @param error
     * @param statusCode
     * @param message
     * @param cause
     */
    public NetworkError(ErrorType error, int statusCode, String message, Exception cause) {
        this.error = error;
        this.statusCode = statusCode;
        this.message = message;
        this.cause = cause;
    }

    /**
     * Construct a new NetworkError
     *
     * @param code
     * @param message
     */
    public NetworkError(ErrorType error, String message) {
        this(error, 0, message, null);
    }

    /**
     * Construct a new NetworkError
     *
     * @param error
     * @param cause
     */
    public NetworkError(ErrorType error, Exception cause) {
        this(error, 0, null, cause);
    }

    public boolean isError(ErrorType error) {
        return error = this.error;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("NetworkError[");
        sb.append("error: ");
        sb.append(this.error);

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
