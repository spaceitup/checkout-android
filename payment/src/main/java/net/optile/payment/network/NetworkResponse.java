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

import com.btelligent.optile.pds.api.rest.model.payment.enterprise.extensible.List;
import com.btelligent.optile.pds.api.rest.model.payment.enterprise.extensible.Charge;

import java.util.HashMap;

/**
 * Class containing response data from the Payment API, the class 
 * contains either a NetworkError or data
 */
public final class NetworkResponse {

    private static String KEY_LIST    = "list";
    private static String KEY_CHARGE  = "charge";
    
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
     * @param message the error message
     * @return        the newly created network error response
     */
    public final static NetworkResponse newInvalidValueResponse(String message) {

        NetworkError error = new NetworkError(ErrorType.INVALID_VALUE, message, 0, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new API Error network response
     *
     * @param message    the error message
     * @param statusCode the network status code
     * @return           the newly created network error response
     */
    public final static NetworkResponse newApiErrorResponse(String message, int statusCode) {

        NetworkError error = new NetworkError(ErrorType.API_ERROR, message, statusCode, null);
        return new NetworkResponse(error);
    }

    /**
     * Create a new connection error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public final static NetworkResponse newConnErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.CONN_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new security error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public final static NetworkResponse newSecurityErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.SECURITY_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }
    
    /**
     * Create a new internal error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public final static NetworkResponse newInternalErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.INTERNAL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new protocol error response
     *
     * @param message the error message
     * @param cause   the optional exception that caused the error
     * @return        the newly created network error response
     */
    public final static NetworkResponse newProtocolErrorResponse(String message, Exception cause) {

        NetworkError error = new NetworkError(ErrorType.PROTOCOL_ERROR, message, 0, cause);
        return new NetworkResponse(error);
    }

    /**
     * Create a new not found response
     *
     * @param message    the error message
     * @param statusCode the statusCode containing the NOT_FOUND value
     * @return           the newly created network error response
     */
    public final static NetworkResponse newNotFoundResponse(String message, int statusCode) {

        NetworkError error = new NetworkError(ErrorType.NOT_FOUND, message, statusCode, null);
        return new NetworkResponse(error);
    }
    
    /**
     * Gets the List from this network response
     *
     * @return the List or null if it does not 
     *         exist in this response
     */
    public List getListSession() {
        return (List)data.get(KEY_LIST);
    }

    /**
     * Puts a List into this NetworkResponse
     *
     * @param list The List object to be stored in this NetworkResponse
     */
    public void putListSession(List list) {
        data.put(KEY_LIST, list);
    }

    /**
     * Gets the Charge from this network response
     *
     * @return the charge or null if it does not 
     *         exist in this response
     */
    public Charge getCharge() {
        return (Charge)data.get(KEY_CHARGE);
    }

    /**
     * Puts a Charge into this NetworkResponse
     *
     * @param charge The Charge object to be stored in this NetworkResponse
     */
    public void putCharge(Charge charge) {
        data.put(KEY_CHARGE, charge);
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
}
