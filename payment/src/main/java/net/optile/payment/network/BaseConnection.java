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

import android.os.Build;
import android.text.TextUtils;

import net.optile.payment.network.NetworkError.ErrorType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The base class for all Payment API implementations
 */
public abstract class BaseConnection {

    final static int TIMEOUT_CONNECT         = 5000;
    final static int TIMEOUT_READ            = 30000;
    
    final static String HEADER_AUTHORIZATION = "Authorization";
    final static String HEADER_ACCEPT        = "Accept";
    final static String HEADER_CONTENT_TYPE  = "Content-Type";
    final static String HEADER_USER_AGENT    = "User-Agent";

    final static String UTF8                 = "UTF-8";
    final static String HTTP_GET             = "GET";
    final static String HTTP_POST            = "POST";

    final static String URI_PATH_API         = "api";
    final static String URI_PATH_LISTS       = "lists";
    final static String URI_PARAM_VIEW       = "view";

    final static String VALUE_VIEW           = "jsonForms,-htmlForms";
    final static String VALUE_VND_JSON       = "application/vnd.optile.payment.enterprise-v1-extensible+json";    
    /** 
     * The cached user agent value
     */
    private static String userAgent;
    
    /**
     * The base host url
     */
    String url;

    /**
     * Construct a new BaseConnection
     *
     * @param url the base url pointing to the API
     */
    public BaseConnection(String url) {

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
     * @return the user agent value to be send 
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
     * This method will try to close the 
     * HttpURLConnection if it exists
     * 
     * @param conn the connection to close
     */
    public void close(HttpURLConnection conn) {

        if (conn != null) {
            conn.disconnect();
        }
    }

    /**
     * Set connection properties
     *
     * @param conn
     */
    private void setConnProperties(HttpURLConnection conn) {

        conn.setConnectTimeout(TIMEOUT_CONNECT);
        conn.setReadTimeout(TIMEOUT_READ);
        conn.setRequestProperty(HEADER_USER_AGENT, getUserAgent());
    }

    /**
     * Creates a new HTTP GET connection
     *
     * @param url                    the Url pointing to the Payment API
     * @return HttpURLConnection     a HttpURLConnection object
     * @throws MalformedURLException URL format is malformed
     * @throws IOException           when i.e. a network error occured
     */
    public HttpURLConnection createGetConnection(String url) throws MalformedURLException,
                                                                    IOException {

        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();

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
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();

        setConnProperties(conn);

        conn.setRequestMethod(HTTP_POST);
        conn.setDoInput(true);
        conn.setDoOutput(true);
            
        return conn;
    }

    /**
     * Reads a String from the given Inputstream.
     *
     * @param conn         the HttpURLConnection to read from
     * @return             the string representation read from the inputstream
     * @throws IOException when an error occured
     */
    public String readFromInputStream(HttpURLConnection conn) throws IOException {

        try (InputStream in = conn.getInputStream();
             InputStreamReader ir = new InputStreamReader(in);
             BufferedReader rd = new BufferedReader(ir)) {

            return readFromBufferedReader(rd);
        }
    }


    /**
     * Reads a String from the error stream
     *
     * @param conn         the HttpURLConnection to read from
     * @return             the string representation read from the inputstream
     * @throws IOException when an error occured
     */
    public String readFromErrorStream(HttpURLConnection conn) throws IOException {

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
     * @param conn
     * @param data 
     */    
    public void writeToOutputStream(HttpURLConnection conn, String data) throws IOException {

        try (OutputStream out = conn.getOutputStream()) {
            out.write(data.getBytes(UTF8));
        }
    }
    
    /** 
     * Read all content as a String from the buffered reader
     * 
     * @return The content from the buffered reader
     */
    private String readFromBufferedReader(BufferedReader in) throws IOException {
        
        String line = null;
        StringBuilder buf = new StringBuilder();

        while ((line = in.readLine()) != null) {
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
     * @param source
     * @param statusCode
     * @param conn
     * @return NetworkResponse
     */
    public NetworkResponse handleAPIErrorResponse(String source, int statusCode, HttpURLConnection conn) throws IOException {

        String msg = source + " - errorData: " + readFromErrorStream(conn);
        ErrorType errorType = ErrorType.API_ERROR;

        switch (statusCode) {
        case HttpURLConnection.HTTP_NOT_FOUND:
            errorType = ErrorType.NOT_FOUND;
        }
        return new NetworkResponse(new NetworkError(errorType, msg, statusCode, null));
    }
}
