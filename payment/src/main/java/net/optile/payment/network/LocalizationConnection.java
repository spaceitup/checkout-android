/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import static net.optile.payment.core.PaymentError.API_ERROR;
import static net.optile.payment.core.PaymentError.CONN_ERROR;
import static net.optile.payment.core.PaymentError.INTERNAL_ERROR;
import static net.optile.payment.core.PaymentError.PROTOCOL_ERROR;
import static net.optile.payment.core.PaymentError.SECURITY_ERROR;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.google.gson.JsonParseException;

import android.net.Uri;
import android.text.TextUtils;
import net.optile.payment.core.PaymentException;
import net.optile.payment.localization.LocalizationHolder;
import net.optile.payment.localization.PropLocalizationHolder;
import net.optile.payment.model.ListResult;

/**
 * Class implementing the communication with the payment API to load localization files
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class LocalizationConnection extends BaseConnection {

    /**
     * Load the localization file given the URL.
     *
     * @param url containing the address of the remote language file
     * @return LocalizationFile object containing the language entries
     */
    public LocalizationHolder loadLocalization(URL url) throws PaymentException {
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        HttpURLConnection conn = null;
        try {
            conn = createGetConnection(url);
            Properties prop = new Properties();
            try (InputStream in = conn.getInputStream();
                InputStreamReader ir = new InputStreamReader(in)) {
                prop.load(ir);
            }
            return new PropLocalizationHolder(prop);
        } catch (IOException e) {
            throw createPaymentException(CONN_ERROR, e);
        } finally {
            close(conn);
        }
    }
}
