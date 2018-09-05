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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;
import android.text.TextUtils;
    
/**
 * A class representing the network error
 */
public final class NetworkError {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            API_ERROR,
            CONN_ERROR,
            INTERNAL_ERROR,
            SECURITY_ERROR,
            INVALID_VALUE,
            NOT_FOUND,
            PROTOCOL_ERROR
            })
    public @interface ErrorType {}

    public final static String API_ERROR        = "API_ERROR";
    public final static String CONN_ERROR       = "CONN_ERROR";
    public final static String INTERNAL_ERROR   = "INTERNAL_ERROR";
    public final static String SECURITY_ERROR   = "SECURITY_ERROR";
    public final static String INVALID_VALUE    = "INVALID_VALUE";
    public final static String NOT_FOUND        = "NOT_FOUND";
    public final static String PROTOCOL_ERROR   = "PROTOCOL_ERROR";

    /**
     * The mandatory error type 
     */
    private String type;

    /**
     * The optional network status code like 400 or 500
     */
    private int statusCode;

    /**
     * The optional error message
     */
    private String message;

    /**
     * The optional exception that caused the error
     */
    private Exception cause;

    /**
     * Construct a new NetworkError
     *
     * @param type
     * @param statusCode
     * @param message
     * @param cause
     */
    public NetworkError(@ErrorType final String type, final String message, final int statusCode, final Exception cause) {
        this.type = type;
        this.message = message;
        this.statusCode = statusCode;
        this.cause = cause;
    }

    /** 
     * Check if this error is of the given type
     * 
     * @param  type the type identifying this error
     * @return      true when it is the same error, false otherwise
     */
    public boolean isError(@ErrorType final String type) {
        return this.type == type;
    }

    /** 
     * Get the ErrorType from this NetworkError
     * 
     * @return the errorType stored in this NetworkError 
     */
    @ErrorType
    public String getType() {
        return type;
    }

    /** 
     * Get the status Code from this network error
     * 
     * 
     * @return the optional status code 
     */
    public int getStatusCode() {
        return statusCode;
    }

    /** 
     * Get the message from this network error
     * 
     * 
     * @return the optional message 
     */
    public String getMessage() {
        return message;
    }

    /** 
     * Get the cause from this network error
     * 
     * 
     * @return the optional cause  
     */
    public Exception getCause() {
        return cause;
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
}
