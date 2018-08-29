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

import static net.optile.payment.network.NetworkConstants.TIMEOUT_CONNECT;
import static net.optile.payment.network.NetworkConstants.TIMEOUT_READ;


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
     * Make a list request to the backend
     *
     * @param tokens
     * @param book
     * @return The NetworkResponse
     */
    public NetworkResponse postListRequest() {

        if (tokens == null || !tokens.containsAccessToken()) {
            return createAuthErrorResponse("createBook - Authentication data is missing");
        }
        if (book == null) {
            return createInvalidArgumentErrorResponse("createBook - book cannot be null");
        }
        ServerResponse resp = null;
        try {
            Uri u = Uri.parse(serverUrl).buildUpon()
                    .appendPath("books").build();

            String jsonParams = gson.toJson(book);
            conn = createJSONPostConnection(u.toString(), tokens, TIMEOUT_READ, TIMEOUT_CONNECT);

            long beforeTs = System.currentTimeMillis();
            out = conn.getOutputStream();
            out.write(jsonParams.getBytes(UTF8));
            out.close();
            conn.connect();

            int rc = conn.getResponseCode();
            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    this.in = conn.getInputStream();
                    String recvData = readInputStream(this.in);
                    logHttpRequest(rc, beforeTs, recvData);
                    resp = handleBookCreatedOk(recvData);
                    break;
                default:
                    logHttpRequest(rc, beforeTs, null);
                    resp = handleErrorResponse("createBook", rc, conn);
            }
        } catch (Exception e) {
            resp = handleException(e);
        } finally {
            close();
        }
        return resp;
    }

    /**
     * Create a book on the backend
     *
     * @param tokens
     * @param book
     * @return The ServerResponse
     */
    public ServerResponse updateBook(AuthTokens tokens, Book book) {

        if (tokens == null || !tokens.containsAccessToken()) {
            return createAuthErrorResponse("updateBook - Authentication data is missing");
        }
        if (book == null) {
            return createInvalidArgumentErrorResponse("updateBook - book cannot be null");
        }
        ServerResponse resp = null;
        try {
            Uri u = Uri.parse(serverUrl).buildUpon()
                    .appendPath("books")
                    .appendPath(book.getRemoteId()).build();

            String jsonParams = gson.toJson(book);
            conn = createJSONPutConnection(u.toString(), tokens, TIMEOUT_READ, TIMEOUT_CONNECT);
            long beforeTs = System.currentTimeMillis();
            out = conn.getOutputStream();
            out.write(jsonParams.getBytes(UTF8));
            out.close();
            conn.connect();

            int rc = conn.getResponseCode();
            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    this.in = conn.getInputStream();
                    String recvData = readInputStream(this.in);
                    logHttpRequest(rc, beforeTs, recvData);
                    resp = handleBookUpdatedOk(recvData);
                    break;
                default:
                    logHttpRequest(rc, beforeTs, null);
                    resp = handleErrorResponse("updateBook", rc, conn);
            }
        } catch (Exception e) {
            resp = handleException(e);
        } finally {
            close();
        }
        return resp;
    }


    /**
     * Delete a book from the backend
     *
     * @param tokens
     * @param book
     * @return The ServerResponse
     */
    public ServerResponse deleteBook(AuthTokens tokens, String bookId) {

        // validate the authentication data
        if (tokens == null || !tokens.containsAccessToken()) {
            return createAuthErrorResponse("deleteBook - Authentication data is missing");
        }
        if (TextUtils.isEmpty(bookId)) {
            return createInvalidArgumentErrorResponse("deleteBook - bookId cannot be null or empty");
        }
        ServerResponse resp = null;
        try {
            Uri u = Uri.parse(serverUrl).buildUpon()
                    .appendPath("books")
                    .appendPath(bookId).build();

            conn = createDeleteConnection(u.toString(), tokens, TIMEOUT_READ, TIMEOUT_CONNECT);
            long beforeTs = System.currentTimeMillis();
            conn.connect();

            int rc = conn.getResponseCode();
            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    this.in = conn.getInputStream();
                    String recvData = readInputStream(this.in);
                    logHttpRequest(rc, beforeTs, recvData);
                    resp = handleBookDeletedOk(recvData);
                    break;
                default:
                    logHttpRequest(rc, beforeTs, null);
                    resp = handleErrorResponse("deleteBook", rc, conn);
            }
        } catch (Exception e) {
            resp = handleException(e);
        } finally {
            close();
        }
        return resp;
    }

    /**
     * Get the books from the backend for this user
     *
     * @param tokens
     * @param limit
     * @param offset
     * @param modifiedSince
     * @return The ServerResponse
     */
    public ServerResponse getBooks(AuthTokens tokens, int limit, int offset, long modifiedSince) {

        // validate the authentication data
        if (tokens == null || !tokens.containsAccessToken()) {
            return createAuthErrorResponse("getBooks - Authentication data is missing");
        }
        if (limit <= 0) {
            return createInvalidArgumentErrorResponse("getBooks - limit must be greater than 0");
        }
        if (offset < 0) {
            return createInvalidArgumentErrorResponse("getBooks - offset must be greater or equal to 0");
        }
        if (modifiedSince < 0) {
            return createInvalidArgumentErrorResponse("getBooks - modifiedSince must be greater or equal to 0");
        }

        ServerResponse resp = null;
        try {

            Uri.Builder builder = Uri.parse(serverUrl).buildUpon().appendPath("books");
            builder.appendQueryParameter(REQ_SINCE, String.valueOf(modifiedSince));
            builder.appendQueryParameter(REQ_LIMIT, String.valueOf(limit));
            builder.appendQueryParameter(REQ_OFFSET, String.valueOf(offset));
            conn = createGetConnection(builder.build().toString(), tokens, TIMEOUT_READ, TIMEOUT_CONNECT);

            long beforeTs = System.currentTimeMillis();
            conn.connect();
            int rc = conn.getResponseCode();
            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    this.in = conn.getInputStream();
                    String recvData = readInputStream(this.in);
                    logHttpRequest(rc, beforeTs, recvData);
                    resp = handleGetBooksOk(recvData);
                    break;
                default:
                    logHttpRequest(rc, beforeTs, null);
                    resp = handleErrorResponse("getBooks", rc, conn);
            }
        } catch (Exception e) {
            resp = handleException(e);
        } finally {
            close();
        }
        return resp;
    }

    /**
     * Handle Book created ok
     *
     * @param data
     * @return The response data.
     * @throws JSONException
     */
    private ServerResponse handleBookCreatedOk(String data) throws JsonParseException {

        Book book = gson.fromJson(data, Book.class);
        if (book == null) {
            throw new JsonParseException("Error parsing Book from data");
        }
        ServerResponse resp = new ServerResponse();
        resp.put(ServerResponse.KEY_BOOK, book);
        return resp;
    }

    /**
     * Handle Books ok
     *
     * @param data
     * @return The response data.
     * @throws JSONException
     */
    private ServerResponse handleGetBooksOk(String data) throws JsonParseException {

        Type listType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        List<Book> books = gson.fromJson(data, listType);

        if (books == null) {
            throw new JsonParseException("Error parsing list of books from data");
        }
        ServerResponse resp = new ServerResponse();
        resp.put(ServerResponse.KEY_BOOKS, books);
        return resp;
    }

    /**
     * Handle Book deleted ok
     *
     * @param data
     * @return The response data.
     * @throws JSONException
     */
    private ServerResponse handleBookDeletedOk(String data) throws JsonParseException {
        return new ServerResponse();
    }

    /**
     * Handle Book updated ok
     *
     * @param data
     * @return The response data.
     * @throws JSONException
     */
    private ServerResponse handleBookUpdatedOk(String data) throws JsonParseException {
        Book book = gson.fromJson(data, Book.class);
        if (book == null) {
            throw new JsonParseException("Error parsing Book from data");
        }
        ServerResponse resp = new ServerResponse();
        resp.put(ServerResponse.KEY_BOOK, book);
        return resp;
    }
}
