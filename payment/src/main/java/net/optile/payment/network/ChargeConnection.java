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

import static net.optile.payment.network.ErrorDetails.API_ERROR;
import static net.optile.payment.network.ErrorDetails.CONN_ERROR;
import static net.optile.payment.network.ErrorDetails.INTERNAL_ERROR;
import static net.optile.payment.network.ErrorDetails.PROTOCOL_ERROR;
import static net.optile.payment.network.ErrorDetails.SECURITY_ERROR;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonParseException;
import org.json.JSONException;

import android.text.TextUtils;
import net.optile.payment.model.OperationResult;
import net.optile.payment.form.Charge;

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
    public OperationResult createCharge(final URL url, final Charge charge) throws NetworkException {
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
                    throw createNetworkException(source, API_ERROR, rc, conn);
            }
        } catch (JsonParseException e) {
            throw createNetworkException(source, PROTOCOL_ERROR, e);
        } catch (MalformedURLException e) {
            throw createNetworkException(source, INTERNAL_ERROR, e);
        } catch (JSONException e) {
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
     * Handle the create charge OK state
     *
     * @param data the response data received from the API
     * @return the network response containing the ListResult
     */
    private OperationResult handleCreateChargeOk(final String data) throws JsonParseException {
        return gson.fromJson(data, OperationResult.class);
    }
}
