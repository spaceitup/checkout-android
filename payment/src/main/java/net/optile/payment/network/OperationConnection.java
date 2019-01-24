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

import static net.optile.payment.core.PaymentError.API_ERROR;
import static net.optile.payment.core.PaymentError.CONN_ERROR;
import static net.optile.payment.core.PaymentError.INTERNAL_ERROR;
import static net.optile.payment.core.PaymentError.PROTOCOL_ERROR;
import static net.optile.payment.core.PaymentError.SECURITY_ERROR;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import com.google.gson.JsonParseException;

import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.OperationResult;

/**
 * Class containing methods to send Operation requests to the Payment API.
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class OperationConnection extends BaseConnection {

    /**
     * Post an operation through the Payment API, i.e. a Preset or Charge operation.
     *
     * @param url the url of the operation
     * @param operation holding the request data
     * @return the OperationResult object received from the Payment API
     */
    public OperationResult postOperation(final URL url, final Operation operation) throws PaymentException {
        final String source = "OperationConnection[postOperation]";

        if (url == null) {
            throw new IllegalArgumentException(source + " - url cannot be null");
        }
        if (operation == null) {
            throw new IllegalArgumentException(source + " - operation cannot be null");
        }
        HttpURLConnection conn = null;

        try {
            conn = createPostConnection(url);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            writeToOutputStream(conn, operation.toJson());
            conn.connect();
            final int rc = conn.getResponseCode();

            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    return handlePostOperationOk(readFromInputStream(conn));
                default:
                    throw createPaymentException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createPaymentException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createPaymentException(source, INTERNAL_ERROR, e);
        } catch (JSONException e) {
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
     * Handle the post operation OK state
     *
     * @param data the response data received from the API
     * @return the network response containing the ListResult
     */
    private OperationResult handlePostOperationOk(final String data) throws JsonParseException {
        return gson.fromJson(data, OperationResult.class);
    }
}
