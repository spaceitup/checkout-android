/**
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

import android.text.TextUtils;

/**
 * A class representing the network error
 */
public final class NetworkError {

    /**
     * The mandatory error type
     */
    public ErrorType type;
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
     * @param type
     * @param statusCode
     * @param message
     * @param cause
     */
    public NetworkError(final ErrorType type, final String message, final int statusCode, final Exception cause) {
        this.type = type;
        this.message = message;
        this.statusCode = statusCode;
        this.cause = cause;
    }

    /**
     * Check if this error is of the given type
     *
     * @param  type the type identifying this error
     * @return true when it is the same error, false otherwise
     */
    public boolean isError(final ErrorType type) {
        return this.type == type;
    }

    /**
     * Get the ErrorType from this NetworkError
     *
     * @return the errorType stored in this NetworkError
     */
    public ErrorType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("NetworkError[");
        sb.append("type: ");
        sb.append(this.type);

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
        return sb.toString();
    }

    public enum ErrorType {

        API_ERROR("API_ERROR"),
        CONN_ERROR("CONN_ERROR"),
        INTERNAL_ERROR("INTERNAL_ERROR"),
        SECURITY_ERROR("SECURITY_ERROR"),
        INVALID_VALUE("INVALID_VALUE"),
        NOT_FOUND("NOT_FOUND"),
        PROTOCOL_ERROR("PROTOCOL_ERROR");

        private String value;

        ErrorType(String v) {
            value = v;
        }

        public String getValue() {
            return value;
        }
    }
}
