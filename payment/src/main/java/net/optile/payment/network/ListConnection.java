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

import android.util.Log;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import net.optile.payment.model.ListResult;

/**
 * Class implementing the communication with the List payment API
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class ListConnection extends BaseConnection {

    /** 
     * The base url i.e. used for creating 
     * a new payment session  
     */
    private String baseUrl;
    
    /**
     * Construct a new ListConnection
     *
     * @param baseUrl The url to be used
     */
    public ListConnection(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Create a new payment session through the Payment API. Remind this is not
     * a request mobile apps should be making as this call is normally executed 
     * Merchant Server-side.
     *
     * @param  authorization the authorization header data
     * @param  listData      the data containing the request body for the list request
     * @return               the NetworkResponse containing either an error or the List
     */
    public NetworkResponse createPaymentSession(String authorization, String listData) {

        String source = "ListConnection[createPaymentSession]";
        
        if (TextUtils.isEmpty(authorization)) {
            return NetworkResponse.newInvalidValueResponse(source + " - authorization cannot be null or empty"); 
        }
        if (TextUtils.isEmpty(listData)) {
            return NetworkResponse.newInvalidValueResponse(source + " - data cannot be null or empty"); 
        }
        
        HttpURLConnection conn = null;
        NetworkResponse resp = null;

        try {

            String requestUrl = Uri.parse(baseUrl).buildUpon()
                .appendPath(URI_PATH_API)
                .appendPath(URI_PATH_LISTS)
                .appendQueryParameter(URI_PARAM_VIEW, VALUE_VIEW)
                .build().toString();

            conn = createPostConnection(requestUrl);
            conn.setRequestProperty(HEADER_AUTHORIZATION, authorization);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            writeToOutputStream(conn, listData);

            conn.connect();
            int rc = conn.getResponseCode();

            switch (rc) {
            case HttpURLConnection.HTTP_OK:
                resp = handleCreatePaymentSessionOk(readFromInputStream(conn));
                break;
            default:
                resp = handleAPIErrorResponse(source, rc, conn);
            }
        } catch (JsonParseException e) {
            resp = NetworkResponse.newProtocolErrorResponse(source, e);            
        } catch (MalformedURLException e) {
            resp = NetworkResponse.newInternalErrorResponse(source, e);
        } catch (IOException e) {
            resp = NetworkResponse.newConnErrorResponse(source, e);
        } catch (SecurityException e) {
            resp = NetworkResponse.newSecurityErrorResponse(source, e);            
        } finally {
            close(conn);
        }
        return resp;
    }

    /**
     * Make a get request to the Payment API in order to 
     * obtain the details of an active list session
     *
     * @param  url  the url pointing to the list
     * @return      the NetworkResponse containing either an error or the ListResult
     */
    public NetworkResponse getListResult(URL url) {

        String source = "ListConnection[getListResult]";        

        if (url == null) {
            return NetworkResponse.newInvalidValueResponse(source + " - url cannot be null or empty"); 
        }

        HttpURLConnection conn = null;
        NetworkResponse resp = null;

        try {

            String requestUrl = Uri.parse(url.toString()).buildUpon()
                .appendQueryParameter(URI_PARAM_VIEW, VALUE_VIEW)
                .build().toString();

            conn = createGetConnection(requestUrl);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            conn.connect();
            int rc = conn.getResponseCode();

            switch (rc) {
            case HttpURLConnection.HTTP_OK:
                resp = handleGetListResultOk(readFromInputStream(conn));
                break;
            default:
                resp = handleAPIErrorResponse(source, rc, conn);
            }
        } catch (JsonParseException e) {
            resp = NetworkResponse.newProtocolErrorResponse(source, e);            
        } catch (MalformedURLException e) {
            resp = NetworkResponse.newInternalErrorResponse(source, e);
        } catch (IOException e) {
            resp = NetworkResponse.newConnErrorResponse(source, e);
        } catch (SecurityException e) {
            resp = NetworkResponse.newSecurityErrorResponse(source, e);            
        } finally {
            close(conn);
        }
        return resp;
    }
    
    /**
     * Handle the create new payment session OK state
     *
     * @param  data the response data received from the API
     * @return      the network response containing the ListResult
     */
    private NetworkResponse handleCreatePaymentSessionOk(String data) throws JsonParseException {

        ListResult result = gson.fromJson(data, ListResult.class);
        NetworkResponse resp = new NetworkResponse();
        resp.putListResult(result);
        return resp;
    }

    /**
     * Handle get list result OK state
     *
     * @param  data the response data received from the Payment API
     * @return      the network response containing the ListResult
     */
    private NetworkResponse handleGetListResultOk(String data) throws JsonParseException {

        ListResult result = gson.fromJson(data, ListResult.class);
        NetworkResponse resp = new NetworkResponse();
        resp.putListResult(result);
        return resp;
    }

}
