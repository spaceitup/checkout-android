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

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static net.optile.payment.core.PaymentError.API_ERROR;
import static net.optile.payment.core.PaymentError.CONN_ERROR;
import static net.optile.payment.core.PaymentError.INTERNAL_ERROR;
import static net.optile.payment.core.PaymentError.PROTOCOL_ERROR;
import static net.optile.payment.core.PaymentError.SECURITY_ERROR;

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
     * Create a new payment session through the Payment API. Remind this is not
     * a request mobile apps should be making as this call is normally executed
     * Merchant Server-side. This request will be removed later.
     *
     * @param baseUrl the base url of the Payment API
     * @param authorization the authorization header data
     * @param listData the data containing the request body for the list request
     * @return the ListResult
     */
    public ListResult createPaymentSession(final String baseUrl, final String authorization, final String listData)
        throws PaymentException {
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
                    throw createPaymentException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createPaymentException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createPaymentException(source, INTERNAL_ERROR, e);
        } catch (IOException e) {
            throw createPaymentException(source, CONN_ERROR, e);
        } catch (SecurityException e) {
            throw createPaymentException(source, SECURITY_ERROR, e);
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
     */
    public ListResult getListResult(final String url) throws PaymentException {
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
                    throw createPaymentException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createPaymentException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createPaymentException(source, INTERNAL_ERROR, e);
        } catch (IOException e) {
            throw createPaymentException(source, CONN_ERROR, e);
        } catch (SecurityException e) {
            throw createPaymentException(source, SECURITY_ERROR, e);
        } finally {
            close(conn);
        }
    }

    /**
     * Load the language file given the URL
     *
     * @param url the pointing to the language entries
     * @param file store the loaded language entries in this LanguageFile
     * @return LanguageFile object containing the language entries
     */
    public LanguageFile loadLanguageFile(final URL url, LanguageFile file) throws PaymentException {
        final String source = "ListConnection[loadLanguageFile]";

        if (url == null) {
            throw new IllegalArgumentException(source + " - url cannot be null");
        }
        HttpURLConnection conn = null;
        try {
            conn = createGetConnection(url);

            try (InputStream in = conn.getInputStream();
                InputStreamReader ir = new InputStreamReader(in)) {
                file.getProperties().load(ir);
            }
            return file;
        } catch (IOException e) {
            throw createPaymentException(source, CONN_ERROR, e);
        } finally {
            close(conn);
        }
    }

    /**
     * Handle the create new payment session OK state
     *
     * @param data the response data received from the API
     * @return the ListResult
     */
    private ListResult handleCreatePaymentSessionOk(final String data) throws JsonParseException {
        return gson.fromJson(data, ListResult.class);
    }

    /**
     * Handle get list result OK state
     *
     * @param data the response data received from the Payment API
     * @return the ListResult
     */
    private ListResult handleGetListResultOk(final String data) throws JsonParseException {
        return gson.fromJson(data, ListResult.class);
    }
}
