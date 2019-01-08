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

import com.google.gson.JsonParseException;

import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.OperationResult;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static net.optile.payment.core.PaymentError.API_ERROR;
import static net.optile.payment.core.PaymentError.CONN_ERROR;
import static net.optile.payment.core.PaymentError.INTERNAL_ERROR;
import static net.optile.payment.core.PaymentError.PROTOCOL_ERROR;
import static net.optile.payment.core.PaymentError.SECURITY_ERROR;

/**
 * Class implementing the communication with the Charge payment API
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class ChargeConnection extends BaseConnection {

    /**
     * Create a new charge through the Payment API
     *
     * @param url the url of the charge
     * @param charge holding the charge request data
     * @return the OperationResult object received from the Payment API
     */
    public OperationResult createCharge(final URL url, final Charge charge) throws PaymentException {
        final String source = "ChargeConnection[createCharge]";

        if (url == null) {
            throw new IllegalArgumentException(source + " - url cannot be null");
        }
        if (charge == null) {
            throw new IllegalArgumentException(source + " - charge cannot be null");
        }
        HttpURLConnection conn = null;

        try {
            conn = createPostConnection(url);
            conn.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_APP_JSON);
            conn.setRequestProperty(HEADER_ACCEPT, VALUE_APP_JSON);

            writeToOutputStream(conn, charge.toJson());
            conn.connect();
            final int rc = conn.getResponseCode();

            switch (rc) {
                case HttpURLConnection.HTTP_OK:
                    return handleCreateChargeOk(readFromInputStream(conn));
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
     * Handle the create charge OK state
     *
     * @param data the response data received from the API
     * @return the network response containing the ListResult
     */
    private OperationResult handleCreateChargeOk(final String data) throws JsonParseException {
        return gson.fromJson(data, OperationResult.class);
    }
}
