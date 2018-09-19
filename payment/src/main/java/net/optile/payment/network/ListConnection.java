/*
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

import static net.optile.payment.network.ErrorDetails.API_ERROR;
import static net.optile.payment.network.ErrorDetails.CONN_ERROR;
import static net.optile.payment.network.ErrorDetails.INTERNAL_ERROR;
import static net.optile.payment.network.ErrorDetails.PROTOCOL_ERROR;
import static net.optile.payment.network.ErrorDetails.SECURITY_ERROR;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.google.gson.JsonParseException;

import android.net.Uri;
import android.text.TextUtils;
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

    private final static String TAG = "payment_ListConnection";

    /**
     * Create a new payment session through the Payment API. Remind this is not
     * a request mobile apps should be making as this call is normally executed
     * Merchant Server-side. This request will be removed later.
     *
     * @param baseUrl the base url of the Payment API
     * @param authorization the authorization header data
     * @param listData the data containing the request body for the list request
     * @return the ListResult
     * @throws NetworkException when an error occurred while making the request
     */
    public ListResult createPaymentSession(final String baseUrl, final String authorization, final String listData) throws NetworkException {
        final String source = "ListConnection[createPaymentSession]";

        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException(source + " - baseUrl cannot be null or empty");
        }
        if (TextUtils.isEmpty(authorization)) {
            throw new IllegalArgumentException(source + " - authorization cannot be null or empty");
        }
        if (TextUtils.isEmpty(listData)) {
            throw new IllegalArgumentException(source + " - data cannot be null or empty");
        }

        HttpURLConnection conn = null;
        try {
            final String requestUrl = Uri.parse(baseUrl).buildUpon()
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
            final int rc = conn.getResponseCode();

            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    return handleCreatePaymentSessionOk(readFromInputStream(conn));
                default:
                    throw createNetworkException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createNetworkException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createNetworkException(source, INTERNAL_ERROR, e);
        } catch (IOException e) {
            throw createNetworkException(source, CONN_ERROR, e);
        } catch (SecurityException e) {
            throw createNetworkException(source, SECURITY_ERROR, e);
        } finally {
            close(conn);
        }
    }

    /**
     * Make a get request to the Payment API in order to
     * obtain the details of an active list session
     *
     * @param url the url pointing to the list
     * @return the NetworkResponse containing either an error or the ListResult
     * @throws NetworkException the network exception
     */
    public ListResult getListResult(final String url) throws NetworkException {
        final String source = "ListConnection[getListResult]";

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException(source + " - url cannot be null or empty");
        }

        HttpURLConnection conn = null;
        try {
            final String requestUrl = Uri.parse(url).buildUpon()
                .appendQueryParameter(URI_PARAM_VIEW, VALUE_VIEW)
                .build().toString();

            conn = createGetConnection(requestUrl);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            conn.connect();
            final int rc = conn.getResponseCode();
            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    return handleGetListResultOk(readFromInputStream(conn));
                default:
                    throw createNetworkException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createNetworkException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createNetworkException(source, INTERNAL_ERROR, e);
        } catch (IOException e) {
            throw createNetworkException(source, CONN_ERROR, e);
        } catch (SecurityException e) {
            throw createNetworkException(source, SECURITY_ERROR, e);
        } finally {
            close(conn);
        }
    }

    /** 
     * Load the language file given the URL into Properties object
     * 
     * @param url the URL pointing to the language entries
     * 
     * @return the Properties object containing the language entries
     * @throws NetworkException when an error occurred while loading the language entries
     */
    public Properties getLanguage(final URL url) throws NetworkException {
        final String source = "ListConnection[getLanguage]";

        if (url == null) {
            throw new IllegalArgumentException(source + " - url cannot be null");
        }
        Properties prop = new Properties();
        try (InputStream in = url.openStream();
             InputStreamReader ir = new InputStreamReader(in)) {
            prop.load(ir);
            return prop;
        } catch (IOException e) {
            throw createNetworkException(source, CONN_ERROR, e);            
        }
    }
    
    /**
     * Handle the create new payment session OK state
     *
     * @param data the response data received from the API
     * @return the ListResult
     * @throws JsonParseException when an error occurred during parsing
     */
    private ListResult handleCreatePaymentSessionOk(final String data) throws JsonParseException {
        return gson.fromJson(data, ListResult.class);
    }

    /**
     * Handle get list result OK state
     *
     * @param data the response data received from the Payment API
     * @return the ListResult
     * @throws JsonParseException when an error occurred during parsing
     */
    private ListResult handleGetListResultOk(final String data) throws JsonParseException {
        return gson.fromJson(data, ListResult.class);
    }
}
