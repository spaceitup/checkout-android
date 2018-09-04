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

import net.optile.payment.model.ListResult;
import net.optile.payment.model.OperationResult;

import java.util.Map;
import java.util.HashMap;
import java.net.URL;

/**
 * Class containing response data from the Payment API, the class 
 * contains either a NetworkError or data
 */
public final class NetworkResponse {

    private static String KEY_LISTRESULT      = "listresult";
    private static String KEY_OPERATIONRESULT = "operationresult";
    
    /**
     * The network error
     */
    public NetworkError error;

    /**
     * The data holder
     */
    public Map<String, Object> data;

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
    public NetworkResponse(final NetworkError error) {
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
     * Check if this response contains the given error
     *
     * @return true if it matches the given error type, false otherwise
     */
    public boolean isError(final NetworkError.ErrorType type) {
        return error != null && error.isError(type);
    }
    
    /**
     * Get the error stored in this NetworkResponse. 
     *
     * @return the error or null if this NetworkResponse does not contain an error
     */
    public NetworkError getError() {
        return error;
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
     * @param message the error message
     * @return        the newly created network error response
     */
    public static NetworkResponse newInvalidValueResponse(final String message) {

        final NetworkError error = new NetworkError(ErrorType.INVALID_VALUE, message, 0, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new API Error network response
     *
     * @param message    the error message
     * @param statusCode the network status code
     * @return           the newly created network error response
     */
    public static NetworkResponse newApiErrorResponse(final String message, final int statusCode) {

        final NetworkError error = new NetworkError(ErrorType.API_ERROR, message, statusCode, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new connection error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public static NetworkResponse newConnErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.CONN_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new security error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public static NetworkResponse newSecurityErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.SECURITY_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }
    
    /**
     * Create a new internal error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public static NetworkResponse newInternalErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.INTERNAL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new protocol error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public static NetworkResponse newProtocolErrorResponse(final String message, final Exception cause) {

        final NetworkError error = new NetworkError(ErrorType.PROTOCOL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new not found response
     *
     * @param message    the error message
     * @param statusCode the statusCode containing the NOT_FOUND value
     * @return           the newly created network error response
     */
    public static NetworkResponse newNotFoundResponse(final String message, final int statusCode) {

        final NetworkError error = new NetworkError(ErrorType.NOT_FOUND, message, statusCode, null);
        return new NetworkResponse(error);
    }
    
    /**
     * Gets the list result from this network response
     *
     * @return the list result or null if it does not 
     *         exist in this response
     */
    public ListResult getListResult() {
        return (ListResult)data.get(KEY_LISTRESULT);
    }

    /**
     * Puts a list result into this NetworkResponse
     *
     * @param list The list result to be stored in this NetworkResponse
     */
    public void putListResult(final ListResult list) {
        data.put(KEY_LISTRESULT, list);
    }

    /**
     * Gets the OperationResult from this network response
     *
     * @return the operation result or null if it does not 
     *         exist in this response
     */
    public OperationResult getOperationResult() {
        return (OperationResult)data.get(KEY_OPERATIONRESULT);
    }

    /**
     * Puts an OperationResult into this NetworkResponse
     *
     * @param operationResult the OperationResult object to be stored in this NetworkResponse
     */
    public void putOperationResult(final OperationResult operationResult) {
        data.put(KEY_OPERATIONRESULT, operationResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("NetworkResponse[");

        if (error == null) {
            sb.append("OK - datakeys");
            sb.append(data.keySet());
        }
        else {
            sb.append("ERROR - ");
            sb.append(error);
        }
        sb.append("]");
        return sb.toString();
    }
}
