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

package net.optile.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.Resources;
import android.util.Log;

/**
 * The PaymentUtils class containing helper methods
 */
public final class PaymentUtils {

    private final static String TAG = "payment_PaymentUtils";

    /**
     * Check if the Boolean object is true, the Boolean object may be null.
     *
     * @param val the value to check
     * @return true when the val is not null and true
     */
    public static boolean isTrue(Boolean val) {
        return val != null && val;
    }

    /**
     * Strips whitespace from the start and end of a String returning an empty String if null input.
     *
     * @param value the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if null input
     */
    public static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * Read the contents of the raw resource
     *
     * @param res The system Resources
     * @param resId The resource id
     * @return The String or an empty string if something went wrong
     */
    public static String readRawResource(Resources res, int resId) {
        StringBuilder sb = new StringBuilder();
        String line;

        try (InputStream is = res.openRawResource(resId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr)) {

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Log.wtf(TAG, e);
        }
        return sb.toString();
    }
}
