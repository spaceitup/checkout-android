/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.network;

import static net.optile.payment.core.PaymentError.CONN_ERROR;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import net.optile.payment.core.PaymentException;

/**
 * Class for loading images from the network
 * <p>
 * All requests in this class are blocking calls and should be
 * executed in a separate thread to avoid blocking the main application thread.
 * These methods are not thread safe and must not be called by different threads
 * at the same time.
 */
public final class ImageConnection extends BaseConnection {

    /**
     * Load the Bitmap from the given URL
     *
     * @param url the pointing to the language entries
     * @param file store the loaded language entries in this LanguageFile
     * @return Bitmap drawable
     */
    public Bitmap loadBitmap(final URL url) throws PaymentException {
        final String source = "ListConnection[loadBitmap]";

        if (url == null) {
            throw new IllegalArgumentException(source + " - url cannot be null");
        }
        HttpURLConnection conn = null;
        try {
            conn = createGetConnection(url);

            try (InputStream in = conn.getInputStream()) {
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            throw createPaymentException(source, CONN_ERROR, e);
        } finally {
            close(conn);
        }
    }
}
