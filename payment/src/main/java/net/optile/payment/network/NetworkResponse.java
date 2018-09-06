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

import net.optile.payment.network.NetworkError.ErrorType;

/**
 * Class containing response data from the Payment API, the class 
 * contains either a NetworkError or data object
 */
public final class NetworkResponse<T> {

    /**
     * The data holder
     */
    private T data;

    /**
     * The network error
     */
    private NetworkError error;

    /**
     * Constructs a new empty NetworkResponse
     */
    public NetworkResponse(T data) {
        this.data = data;
    }

    /**
     * Constructs a NetworkResponse with an error
     *
     * @param error The network error
     */
    public NetworkResponse(final NetworkError error) {
        this.error = error;
    }

    /**
     * Create a new invalid value network response
     *
     * @param message the error message
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newInvalidValueResponse(final String message) {

        final NetworkError error = new NetworkError(ErrorType.INVALID_VALUE, message, 0, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new API Error network response
     *
     * @param message    the error message
     * @param statusCode the network status code
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newApiErrorResponse(final String message, final int statusCode) {

        final NetworkError error = new NetworkError(ErrorType.API_ERROR, message, statusCode, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new connection error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newConnErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.CONN_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new security error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newSecurityErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.SECURITY_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new internal error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newInternalErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.INTERNAL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new protocol error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newProtocolErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.PROTOCOL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new not found response
     *
     * @param message    the error message
     * @param statusCode the statusCode containing the NOT_FOUND value
     * @return the newly created network error response
     */
    public static NetworkResponse<T> newNotFoundResponse(final String message, final int statusCode) {

        final NetworkError error = new NetworkError(ErrorType.NOT_FOUND, message, statusCode, null);
        return new NetworkResponse<T>(error);
    }

    /**
     * get the optional data object
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * get the optional error
     *
     * @return the error
     */
    public NetworkError getError() {
        return error;
    }

    /**
     * Check if this response contains any error
     *
     * @return true when an error is present, false otherwise
     */
    public boolean hasError() {
        return error != null;
    }

    /**
     * Check if this response contains the given error
     *
     * @return true if it matches the given error type, false otherwise
     */
    public boolean hasError(final NetworkError.ErrorType type) {
        return error != null && error.isError(type);
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("NetworkResponse[");

        if (data != null) {
            sb.append("OK - ");
            sb.append(data);
        } else {
            sb.append("ERROR - ");
            sb.append(error);
        }
        sb.append("]");
        return sb.toString();
    }
}
