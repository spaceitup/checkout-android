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

import android.net.Uri;
import android.text.TextUtils;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.io.IOException;

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
     * Construct a new ListConnection
     *
     * @param url The url to be used
     */
    public ListConnection(String url) {
        super(url);
    }

    /**
     * Make a new list request to the Payment API
     *
     * @param authorization The authorization header data
     * @return The NetworkResponse
     */
    public NetworkResponse createListRequest(String authorization) {

        if (TextUtils.isEmpty(authorization)) {
            return NetworkResponse.newInvalidValueResponse("authorization cannot be null or empty"); 
        }

        String source = "ListConnection[createListRequest]";
        HttpURLConnection conn = null;
        NetworkResponse resp = null;

        try {

            Uri.Builder builder = Uri.parse(url).buildUpon().appendPath(URI_PATH_API).appendPath(URI_PATH_LISTS);
            builder.appendQueryParameter(URI_PARAM_VIEW, VALUE_VIEW);

            String data = "{}";
            conn = createPostConnection(builder.build().toString());
            conn.setRequestProperty(HEADER_AUTHORIZATION, authorization);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_VND_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_VND_JSON);

            writeToOutputStream(conn, data);

            conn.connect();
            int rc = conn.getResponseCode();

            switch (rc) {
            case HttpURLConnection.HTTP_OK:
                resp = handleCreateListRequestOk(readFromInputStream(conn));
                break;
            default:
                resp = handleAPIErrorResponse(source, rc, conn);
            }
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
     * Handle the create list request OK state
     *
     * @param data
     * @return The network response
     */
    private NetworkResponse handleCreateListRequestOk(String data) {
        return new NetworkResponse();
    }
}
