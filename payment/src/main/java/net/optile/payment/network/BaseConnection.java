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

import static net.optile.payment.network.NetworkConstants.HEADER_USER_AGENT;
import static net.optile.payment.network.NetworkConstants.HTTP_DELETE;
import static net.optile.payment.network.NetworkConstants.HTTP_GET;
import static net.optile.payment.network.NetworkConstants.HTTP_PATCH;
import static net.optile.payment.network.NetworkConstants.HTTP_POST;
import static net.optile.payment.network.NetworkConstants.HTTP_PUT;

/**
 * This is the base connection for all API Connection implementations
 */
public abstract class BaseConnection {

    /**
     * The base host url
     */
    String url;

    /**
     * The log tag
     */
    String tag;

    /** 
     * The user agent value
     */
    String userAgent;

    
    /**
     * Construct a new BaseConnection
     *
     * @param url       The base url pointing to the API
     */
    public BaseConnection(String url) {

        this.url = url;

        // This is needed for older versions of Android
        disableConnectionReuseIfNecessary();

        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }

        initDefaultUserAgent();
    }

    /**
     * Get the user agent
     *
     * @param type
     */
    private void initDefaultUserAgent() {
        StringBuilder sb = new StringBuilder("Android");

        // append the sdk int value
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
        this.userAgent = sb.toString();
    }

    /**
     * This method will try to close both the inputstream,
     * outputstream and connection
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
    private void setConnectionProperties(HttpURLConnection conn, int readTimeout, int connectTimeout) {

        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setRequestProperty("User-Agent", this.userAgent);

    }

    /**
     * Creates a JSON HTTP POST connection.
     *
     * @param url            The url for the connection
     * @param tokens         The authentication tokens
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpURLConnection The HttpURLConnection object
     * @throws IOException           I/O related exception.
     * @throws MalformedURLException Url is not valid.
     */
    public HttpURLConnection createJSONPostConnection(String url, AuthTokens tokens, int readTimeout, int connectTimeout) throws IOException {

        URL u = new URL(url);
        conn = (HttpURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestMethod(HTTP_POST);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
        conn.setRequestProperty("Accept", "application/json");

        return conn;
    }

    /**
     * Creates a HTTP PATCH connection.
     *
     * @param url            The connection url.
     * @param tokens         The authentication tokens
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpURLConnection      The HttpURLConnection object
     * @throws MalformedURLException Url is invalid.
     * @throws IOException           I/O related error
     */
    public HttpURLConnection createPatchConnection(String url, AuthTokens tokens, int readTimeout, int connectTimeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestMethod(HTTP_PATCH);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        return conn;
    }

    /**
     * Creates a HTTP PUT connection specific to the AWS specifics.
     *
     * @param url            The connection url
     * @param tokens         The authentication tokens
     * @param contentType    Content type for header
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpsURLConnection     HttpsURLConnection object
     * @throws MalformedURLException Url is invalid
     * @throws IOException           I/O related errors
     */
    public HttpsURLConnection createAWSPutConnection(String url, AuthTokens tokens, String contentType, int readTimeout, int connectTimeout)
        throws IOException {

        URL u = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestMethod(HTTP_PUT);
        conn.setRequestProperty(HEADER_CONTENT_TYPE, contentType);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        return conn;
    }

    /**
     * Creates a JSON HTTP PUT connection.
     *
     * @param url            The connection url
     * @param tokens         The authentication tokens
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpURLConnection     The HttpURLConnection object
     * @throws MalformedURLException Url is invalid
     * @throws IOException           I/O related errors
     */
    public HttpURLConnection createJSONPutConnection(String url, AuthTokens tokens, int readTimeout, int connectTimeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod(HTTP_PUT);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        return conn;
    }

    /**
     * Creates a HTTP DELETE connection.
     *
     * @param url            The connection url
     * @param tokens         The Authentication tokens
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpURLConnection     The HttpURLConnection object
     * @throws MalformedURLException Url is invalid
     * @throws IOException           I/O related errors
     */
    public HttpURLConnection createDeleteConnection(String url, AuthTokens tokens, int readTimeout, int connectTimeout) throws IOException {

        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestMethod(HTTP_DELETE);
        conn.setDoInput(true);
        conn.setDoOutput(false);

        return conn;
    }

    /**
     * Creates a HTTP GET connection
     *
     * @param url            The url
     * @param tokens         The authentication tokens
     * @param readTimeout    The connection read timeout
     * @param connectTimeout The connection connect timeout
     * @return HttpURLConnection     A HttpURLConnection object
     * @throws MalformedURLException url is invalid
     * @throws IOException           IOException
     */
    public HttpURLConnection createGetConnection(String url, AuthTokens tokens, int readTimeout, int connectTimeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        // set the shared connection properties
        setConnectionProperties(conn, tokens, readTimeout, connectTimeout);

        conn.setRequestMethod(HTTP_GET);
        conn.setDoInput(true);
        conn.setDoOutput(false);

        return conn;
    }

    /**
     * Crafts an invalid argument error response data.
     *
     * @param msg The error message
     * @return The ServerResponse object
     */
    public ServerResponse createInvalidArgumentErrorResponse(String msg) {
        ServiceError error = new ServiceError(ServiceError.INVALID_ARGUMENT, msg);
        return new ServerResponse(error);
    }

    /**
     * Create a new Internal error response
     *
     * @param exception The exception that caused the internal error
     * @return The ServerResponse object
     */
    public ServerResponse createInternalErrorResponse(Exception exception) {
        ServiceError error = new ServiceError(ServiceError.INTERNAL_ERROR, exception);
        return new ServerResponse(error);
    }


    /**
     * Crafts an authentication error response data.
     *
     * @param msg The error message
     * @return The ServerResponse object
     */
    public ServerResponse createAuthErrorResponse(String msg) {
        ServiceError error = new ServiceError(ServiceError.AUTH_ERROR, msg);
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

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            buf.append(line);
        }
        // Return full string
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
     * Handle the backend status error.
     *
     * @param type
     * @param statusCode
     * @param conn
     * @return
     */
    public ServerResponse handleErrorResponse(String type, int statusCode, HttpURLConnection conn) throws IOException {

        this.in = conn.getErrorStream();
        String errorData = readInputStream(this.in);
        ServiceError error = null;

        // parse the response code
        switch (statusCode) {
        case HttpURLConnection.HTTP_UNAUTHORIZED:
            error = new ServiceError(ServiceError.AUTH_ERROR, statusCode, type + " - " + errorData);
            break;
        case HttpURLConnection.HTTP_NOT_FOUND:
            error = new ServiceError(ServiceError.NOT_FOUND, statusCode, type + " - " + errorData);
            break;
        case HttpURLConnection.HTTP_FORBIDDEN:
            error = new ServiceError(ServiceError.NOT_FOUND, statusCode, type + " - " + errorData);
            break;
        case HttpURLConnection.HTTP_BAD_REQUEST:
        case HttpURLConnection.HTTP_INTERNAL_ERROR:
            error = new ServiceError(ServiceError.SERVER_ERROR, statusCode, type + " - " + errorData);
            break;
        default:
            String msg = type + " - Unexpected status code: " + statusCode + ", errorData: " + errorData;
            error = new ServiceError(ServiceError.PROTOCOL_ERROR, statusCode, msg);
        }
        Log.w(tag, "handleErrorResponse: " + error.toString());
        return new ServerResponse(error);
    }

    /**
     * Handle an unexpected Exception while executing one of the HTTP requests
     *
     * @param e the exception to handle
     * @return The NetworkResponse with a NetworkError explaining the error
     */
    public NetworkResponse handleException(Exception e) {

        //Log.w(tag, e);

        int r = NetworkError.PROTOCOL_ERROR;

        if (e instanceof IOException) {
            r = NetworkError.CONN_ERROR;
        }

        return new NetworkResponse(new NetworkError(r, e));
    }
}
