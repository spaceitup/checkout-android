/*
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.model.ErrorInfo;

/**
 * The base class for all Payment API implementations
 */
abstract class BaseConnection {

    final static int TIMEOUT_CONNECT = 5000;
    final static int TIMEOUT_READ = 30000;
    
    final static String HEADER_AUTHORIZATION = "Authorization";
    final static String HEADER_ACCEPT = "Accept";
    final static String HEADER_CONTENT_TYPE = "Content-Type";
    final static String HEADER_USER_AGENT = "User-Agent";

    final static String UTF8 = "UTF-8";

    final static String HTTP_GET = "GET";
    final static String HTTP_POST = "POST";

    final static String URI_PATH_API = "api";
    final static String URI_PATH_LISTS = "lists";
    final static String URI_PARAM_VIEW = "view";

    final static String VALUE_VIEW = "jsonForms,-htmlForms";
    final static String VALUE_APP_JSON = "application/json;charset=UTF-8";

    final static String CONTENTYPE_JSON = "application/json";

    /**
     * The cached user agent value
     */
    private static volatile String userAgent;

    /**
     * For now we will use Gson to parse json content
     * This will be changed at a later stage as no external
     * libraries should be used
     */
    final Gson gson;

    /**
     * Construct a new BaseConnection
     */
    BaseConnection() {

        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
        this.gson = new GsonBuilder().create();
    }

    /**
     * Get the user agent to be send with each request
     *
     * @return the user agent value to be send
     */
    private static String getUserAgent() {
        if (userAgent != null) {
            return userAgent;
        }
        synchronized (BaseConnection.class) {

            if (userAgent == null) {
                StringBuilder sb = new StringBuilder("Android");

                sb.append("/");
                sb.append(Integer.toString(Build.VERSION.SDK_INT));

                if (!TextUtils.isEmpty(Build.VERSION.RELEASE)) {
                    sb.append("/");
                    sb.append(Build.VERSION.RELEASE);
                }

                if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
                    sb.append(" ");
                    sb.append(Build.MANUFACTURER);
                }

                if (!TextUtils.isEmpty(Build.MODEL)) {
                    sb.append("/");
                    sb.append(Build.MODEL);
                }
                userAgent = sb.toString();
            }
        }
        return userAgent;
    }

    /**
     * This method will try to close the
     * HttpURLConnection if it exists
     *
     * @param conn the connection to close
     */
    void close(final HttpURLConnection conn) {

        if (conn != null) {
            conn.disconnect();
        }
    }

    /**
     * Creates a new HTTP GET connection given the String url
     *
     * @param url the url pointing to the Payment API
     * @return HttpURLConnection a HttpURLConnection object
     * @throws IOException when i.e. a network error occurred
     */
    HttpURLConnection createGetConnection(final String url) throws IOException {
        return createGetConnection(new URL(url));
    }

    /**
     * Creates a new HTTP GET connection
     *
     * @param url the Url pointing to the Payment API
     * @return HttpURLConnection a HttpURLConnection object
     * @throws IOException when i.e. a network error occurred
     */
    HttpURLConnection createGetConnection(final URL url) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setConnProperties(conn);
        conn.setRequestMethod(HTTP_GET);
        conn.setDoInput(true);
        conn.setDoOutput(false);
        return conn;
    }

    /**
     * Creates an HTTP POST connection with the given String url
     *
     * @param url the url for the connection
     * @return HttpURLConnection the created HttpURLConnection
     * @throws IOException I/O related exception.
     */
    HttpURLConnection createPostConnection(final String url) throws IOException {
        return createPostConnection(new URL(url));
    }

    /**
     * Creates a HTTP POST connection
     *
     * @param url the url for the connection
     * @return HttpURLConnection the created HttpURLConnection
     * @throws IOException I/O related exception.
     */
    HttpURLConnection createPostConnection(final URL url) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setConnProperties(conn);
        conn.setRequestMethod(HTTP_POST);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    /**
     * Reads a String from the given Inputstream.
     *
     * @param conn the HttpURLConnection to read from
     * @return the string representation read from the Inputstream
     * @throws IOException when an error occurred
     */
    String readFromInputStream(final HttpURLConnection conn) throws IOException {

        try (InputStream in = conn.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader rd = new BufferedReader(ir)) {
            return readFromBufferedReader(rd);
        }
    }

    /**
     * Reads a String from the error stream
     *
     * @param conn the HttpURLConnection to read from
     * @return the string representation read from the inputstream
     * @throws IOException when an error occurred
     */
    String readFromErrorStream(final HttpURLConnection conn) throws IOException {

        if (conn.getErrorStream() == null) {
            return null;
        }
        try (InputStream in = conn.getErrorStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader rd = new BufferedReader(ir)) {
            return readFromBufferedReader(rd);
        }
    }

    /**
     * Write the data to the OutputStream of the
     * HttpURLConnection with UTF8 encoding
     *
     * @param conn the conn
     * @param data the data
     * @throws IOException the io exception
     */
    void writeToOutputStream(final HttpURLConnection conn, String data) throws IOException {

        try (OutputStream out = conn.getOutputStream()) {
            out.write(data.getBytes(UTF8));
        }
    }

    /**
     * Handle the error response from the Payment API
     *
     * @param source the source
     * @param errorType the error type
     * @param statusCode the status code
     * @param conn the conn
     * @return NetworkException network exception
     */
    NetworkException createNetworkException(final String source, final String errorType, final int statusCode,
        final HttpURLConnection conn) {
        String data = null;
        ErrorInfo info = null;

        try {
            data = readFromErrorStream(conn);
            String contentType = conn.getContentType();

            if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(contentType) && contentType.contains(CONTENTYPE_JSON)) {
                info = gson.fromJson(data, ErrorInfo.class);
            }
        } catch (IOException | JsonParseException e) {
            // Ignore the exceptions since the ErrorInfo is an optional field
            // and it is more important to not loose the status error code
            Log.wtf(source, e);
        }
        final ErrorDetails details = new ErrorDetails(source, errorType, statusCode, data, info);
        return new NetworkException(details, source);
    }

    /**
     * Handle the error response from the Payment API
     *
     * @param source the source
     * @param errorType the error type
     * @param cause the cause
     * @return NetworkResponse network exception
     */
    NetworkException createNetworkException(final String source, String errorType, Exception cause) {
        final ErrorDetails details = new ErrorDetails(source, errorType, 0, null, null);
        return new NetworkException(details, source, cause);
    }

    /**
     * Set connection properties
     *
     * @param conn the url connection
     */
    private void setConnProperties(final HttpURLConnection conn) {
        conn.setConnectTimeout(TIMEOUT_CONNECT);
        conn.setReadTimeout(TIMEOUT_READ);
        conn.setRequestProperty(HEADER_USER_AGENT, getUserAgent());
    }

    /**
     * Read all content as a String from the buffered reader
     *
     * @return The content from the buffered reader
     */
    private String readFromBufferedReader(final BufferedReader in) throws IOException {
        final StringBuilder buf = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            buf.append(line);
        }
        return buf.toString();
    }
}
