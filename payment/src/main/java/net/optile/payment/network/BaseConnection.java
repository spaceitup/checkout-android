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

package net.optile.sdk.network;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import net.optile.payment.network.NetworkError.ErrorType;

/**
 * The base class for all Payment API implementations
 */
public abstract class BaseConnection {

    final static int TIMEOUT_CONNECT        = 5000;
    final static int TIMEOUT_READ           = 30000;
    
    final static String HEADER_ACCEPT       = "Accept";
    final static String HEADER_CONTENT_TYPE = "Content-Type";
    final static String HEADER_USER_AGENT   = "User-Agent";

    final static String UTF8                = "UTF-8";
    final static String HTTP_GET            = "GET";
    final static String HTTP_POST           = "POST";

    final static String URI_PATH_API        = "api";
    final static String URI_PATH_LISTS      = "lists";
    final static String URI_PARAM_VIEW      = "view";

    final static String VALUE_VIEW          = "jsonForms,-htmlForms";
    final static String VALUE_VND_JSON      = "application/vnd.optile.payment.enterprise-v1-extensible+json";    
    /** 
     * The cached user agent value
     */
    private static String userAgent;
    
    /**
     * The base host url
     */
    String url;

    /**
     * The log tag
     */
    String tag;

    
    /**
     * Construct a new BaseConnection
     *
     * @param url       The base url pointing to the API
     */
    public BaseConnection(String url, String tag) {

        this.url = url;

        // This is needed for older versions of Android
        disableConnectionReuseIfNecessary();

        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
    }

    /**
     * Get the user agent to be send with each request
     *
     * @return The user agent value to be send 
     */
    public static String getUserAgent() {

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
     * This method will try to close both the inputstream,
     * outputstream and connection.
     * 
     * @param conn
     * @param in
     * @param out 
     */
    public void close(HttpURLConnection conn, InputStream in, OutputStream out) {

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
            }
            out = null;
        }

        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
            in = null;
        }

        if (conn != null) {
            conn.disconnect();
        }
    }

    /**
     * Set connection properties
     *
     * @param conn
     * @param readTimeout
     * @param connectTimeout
     */
    private void setConnProperties(HttpURLConnection conn) {

        conn.setConnectTimeout(TIMEOUT_CONNECT);
        conn.setReadTimeout(TIMEOUT_READ);
        conn.setRequestProperty(HEADER_USER_AGENT, getUserAgent());
    }

    /**
     * Creates a new HTTP GET connection
     *
     * @param url   The Url to connect to
     *
     * @return HttpURLConnection     A HttpURLConnection object
     * @throws MalformedURLException URL creation failed
     * @throws IOException           IOException
     */
    public HttpURLConnection createGetConnection(String url) throws MalformedURLException,
                                                                    IOException {

        URL u = new URL(url);
        HttpURLConnection conn = u.openConnection();

        setConnProperties(conn);

        conn.setRequestMethod(HTTP_GET);
        conn.setDoInput(true);
        conn.setDoOutput(false);

        return conn;
    }

    /**
     * Creates a HTTP POST connection
     *
     * @param url   The url for the connection
     *
     * @return HttpURLConnection     The created HttpURLConnection
     * @throws MalformedURLException URL creation failed
     * @throws IOException           I/O related exception.
     */
    public HttpURLConnection createPostConnection(String url) throws MalformedURLException,
                                                                     IOException {

        URL u = new URL(url);
        HttpURLConnection conn = u.openConnection();

        setConnectionProperties(conn);

        conn.setRequestMethod(HTTP_POST);
        conn.setDoInput(true);
        conn.setDoOutput(true);
            
        return conn;
    }

    /**
     * Crafts an invalid argument error response
     *
     * @param msg The error message
     * @return The ServerResponse object
     */
    public NetworkResponse createInvalidArgumentErrorResponse(String msg) {
        NetworkError error = new NetworkError(ErrorType.INVALID_ARGUMENT, msg);
        return new ServerResponse(error);
    }

    /**
     * Create a new Internal error response
     *
     * @param exception The exception that caused the internal error
     * @return The ServerResponse object
     */
    public NetworkResponse createInternalErrorResponse(Exception exception) {
        NetworkError error = new NetworkError(ErrorType.INTERNAL_ERROR, exception);
        return new ServerResponse(error);
    }

    /**
     * @param conn
     * @param length
     */
    public void setFixedLengthMode(HttpURLConnection conn, long length) {

        // do not set fixed length if length is <= 0
        if (length <= 0) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            conn.setFixedLengthStreamingMode(length);
        } else {
            conn.setFixedLengthStreamingMode((int) length);
        }
    }

    /**
     * Reads a String from the given Inputstream.
     *
     * @param is The inputstream to read from
     * @return The string representation read from the inputstream
     * @throws IOException when an error occured
     */
    public String readInputStream(InputStream is) throws IOException {

        String line = null;
        StringBuilder buf = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        while ((line = rd.readLine()) != null) {
            buf.append(line);
        }
        return buf.toString();
    }

    /**
     * HTTP connection reuse which was buggy pre-froyo release
     * so we disable the keep alive function
     */
    private void disableConnectionReuseIfNecessary() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    /**
     * Handle the error response from the Payment API
     *
     * @param type
     * @param statusCode
     * @param conn
     * @return NetworkResponse
     */
    public NetworkResponse handleErrorResponse(String type, int statusCode, HttpURLConnection conn) throws IOException {

        try (InputStream in = conn.getErrorStream()) {

            String errorData = readInputStream(in);
            NetworkError error = null;

            switch (statusCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                error = new NetworkError(ErrorType.API_ERROR, statusCode, type + " - " + errorData);
                break;
            default:
                String msg = type + " - Unexpected status code: " + statusCode + ", errorData: " + errorData;
                error = new NetworkError(ErrorType.PROTOCOL_ERROR, statusCode, msg);
            }
            return new NetworkResponse(error);
        }
    }

    /**
     * Handle an unexpected Exception while executing one of the HTTP requests
     *
     * @param cause the exception cause to handle
     * @return The NetworkResponse with a NetworkError explaining the error
     */
    public NetworkResponse handleException(Exception cause) {

        //Log.w(tag, e);
        ErrorType errorType = ErrorType.PROTOCOL_ERROR;

        if (e instanceof IOException) {
            errorType = ErrorType.CONN_ERROR;
        }
        return new NetworkResponse(new NetworkError(errorType, cause));
    }
}
