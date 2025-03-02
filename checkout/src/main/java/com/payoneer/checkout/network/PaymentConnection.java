/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import com.google.gson.JsonParseException;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.OperationResult;

import android.content.Context;

/**
 * Class containing methods to send Payment Operation requests to the Payment API.
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class PaymentConnection extends BaseConnection {

    /**
     * Construct a new PaymentConnection
     *
     * @param context used to construct the custom UserAgent header
     */
    public PaymentConnection(Context context) {
        super(context);
    }

    /**
     * Post an operation to the Payment API, i.e. a Preset or Charge operation.
     *
     * @param operation holding the request data
     * @return the OperationResult object received from the Payment API
     */
    public OperationResult postOperation(final Operation operation) throws PaymentException {
        if (operation == null) {
            throw new IllegalArgumentException("operation cannot be null");
        }
        HttpURLConnection conn = null;

        try {
            conn = createPostConnection(operation.getURL());
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            writeToOutputStream(conn, operation.toJson());
            conn.connect();
            final int rc = conn.getResponseCode();
            if (rc == HttpURLConnection.HTTP_OK) {
                return handlePostOperationOk(readFromInputStream(conn));
            }
            throw createPaymentException(rc, conn);
        } catch (MalformedURLException | SecurityException e) {
            throw createPaymentException(e, false);
        } catch (IOException e) {
            throw createPaymentException(e, true);
        } finally {
            close(conn);
        }
    }

    /**
     * Handle the post Operation OK state
     *
     * @param data the response data received from the API
     * @return the network response containing the OperationResult
     */
    private OperationResult handlePostOperationOk(final String data) throws JsonParseException {
        return gson.fromJson(data, OperationResult.class);
    }
}
