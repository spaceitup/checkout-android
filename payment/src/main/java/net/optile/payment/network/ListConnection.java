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
        super(url, "payment_ListConnection");
    }

    /**
     * Make a new list request to the Payment API
     *
     * @param authentication The authentication header data
     * @return The NetworkResponse
     */
    public NetworkResponse createListRequest(String authentication) {

        if (TextUtils.isEmpty(authentication)) {
            return NetworkResponse.newInvalidValueResponse("authentication cannot be null or empty"); 
        }

        NetworkResponse resp   = null;
        HttpURLConnection conn = null;
        InputStream in         = null;
        OutputStream out       = null;

        try {

            Uri.Builder builder = Uri.parse(url).buildUpon().appendPath(URI_PATH_API).appendPath(URI_PATH_LISTS);
            builder.appendQueryParameter(URI_PARAM_VIEW, VALUE_VIEW);

            String jsonParams = "{}";
            conn = createPostConnection(uri.build().toString());
            conn.setRequestProperty(HEADER_AUTHORIZATION, authentication);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_VND_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_VND_JSON);
            
            out = conn.getOutputStream();
            out.write(jsonParams.getBytes(UTF8));

            conn.connect();
            int rc = conn.getResponseCode();

            switch (rc) {
            case HttpURLConnection.HTTP_OK:
                in = conn.getInputStream();
                resp = handleCreateListRequestOk(readInputStream(in));
                break;
            default:
                resp = handleErrorResponse("createListRequest", rc, conn);
            }
        } catch (Exception e) {
            resp = handleException(e);
        } finally {
            close(conn, in, out);
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
