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

import net.optile.payment.network.NetworkError.ErrorType;

import java.util.HashMap;

/**
 * Class containing response data from the Payment API, the class 
 * contains either a NetworkError or data
 */
public final class NetworkResponse {

    /**
     * The network error
     */
    public NetworkError error;

    /**
     * The data holder
     */
    public HashMap<String, Object> data;

    /**
     * Constructs a new empty NetworkResponse
     */
    public NetworkResponse() {
        data = new HashMap<String, Object>();
    }

    /**
     * Constructs a NetworkResponse with an error
     *
     * @param error The network error
     */
    public NetworkResponse(NetworkError error) {
        this.error = error;
    }

    /**
     * Check if this response contains an error
     *
     * @return true when an error is present, false otherwise
     */
    public boolean hasError() {
        return error != null;
    }

    /**
     * Does this server response contain a network connection error
     *
     * @return true when it has a connection error, false otherwise
     */
    public boolean hasConnectionError() {
        return error != null && error.isError(ErrorType.CONN_ERROR);
    }

    /**
     * Create a new invalid value network response
     *
     * @param message   The error message
     * @return The newly created network error response
     */
    public final static NetworkResponse newInvalidValueResponse(String message) {

        NetworkError error = new NetworkError(ErrorType.INVALID_VALUE, message, 0, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new API Error network response
     *
     * @param message       The error message
     * @param statusCode    The network status code
     * @return The newly created network error response
     */
    public final static NetworkResponse newApiErrorResponse(String message, int statusCode) {

        NetworkError error = new NetworkError(ErrorType.API_ERROR, message, statusCode, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new connection error response
     *
     * @param message   The error message
     * @param cause     The optional exception that caused the error
     * @return The newly created network error response
     */
    public final static NetworkResponse newConnErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.CONN_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new security error response
     *
     * @param message   The error message
     * @param cause     the optional exception that caused the error
     * @return The newly created network error response
     */
    public final static NetworkResponse newSecurityErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.SECURITY_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }
    
    /**
     * Create a new internal error response
     *
     * @param message   The error message
     * @param cause     the optional exception that caused the error
     * @return The newly created network error response
     */
    public final static NetworkResponse newInternalErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.INTERNAL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new protocol error response
     *
     * @param message   The error message
     * @param cause     the optional exception that caused the error
     * @return The newly created network error response
     */
    public final static NetworkResponse newProtocolErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.PROTOCOL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new not found response
     *
     * @param message       The error message
     * @param statusCode    The statusCode containing the NOT_FOUND value
     * @return The newly created network error response
     */
    public final static NetworkResponse newNotFoundResponse(String message, int statusCode) {

        NetworkError error = new NetworkError(ErrorType.NOT_FOUND, message, statusCode, null);
        return new NetworkResponse(error);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NetworkResponse[");

        if (error == null) {
            sb.append("OK");
        }
        else {
            sb.append("ERROR: ");
            sb.append(error);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Gets the integer value from this NetworkResponse object.
     *
     * @param key The key for this value.
     * @return The integer value or 0 when the value could not be found or -1
     * if the value Object was not an Integer.
     */
    public int getInt(String key) {

        Object v = data.get(key);
        if (v == null) {
            return 0;
        }
        return (v instanceof Integer) ? (Integer) v : -1;
    }

    /**
     * Gets the String value given the key.
     *
     * @param key The key for this value.
     * @return null if the value was not stored in this NetworkResponse
     * or the String value for the given key.
     */
    public String getString(String key) {

        Object v = data.get(key);
        if (v == null) {
            return null;
        }
        return (v instanceof String) ? (String) v : v.toString();
    }

    /**
     * Gets the String value given the key.
     *
     * @param key      The key for this value.
     * @param defValue The default value returned if the key was not found.
     * @return The default value, or the String value
     * mapped by this key.
     */
    public String getString(String key, String defValue) {

        Object v = data.get(key);
        if (v == null) {
            return defValue;
        }
        return (v instanceof String) ? (String) v : v.toString();
    }

    /**
     * Gets the boolean value given the key.
     *
     * @param key The key for this value.
     * @return false if they key was not found or the boolean value
     * stored in this NetworkResponse object otherwise.
     */
    public boolean getBoolean(String key) {

        Object v = data.get(key);
        return v != null && (v instanceof Boolean) && ((Boolean) v).booleanValue();
    }

    /**
     * Gets the Object mapped to the key.
     *
     * @param key The key for this value.
     * @return null if the key is not found, otherwise
     * the value of the Object this key is mapped to.
     */
    public Object getObject(String key) {
        return data.get(key);
    }

    /**
     * Puts a value into this NetworkResponse object given the key.
     *
     * @param key   The key for which the value should be mapped.
     * @param value The actual value mapped to the key.
     */
    @SuppressWarnings("unchecked")
    public void put(String key, Object value) {
        if (key != null && value != null) {
            data.put(key, value);
        }
    }
}
