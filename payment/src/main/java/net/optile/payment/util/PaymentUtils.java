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

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.Resources;

/**
 * The PaymentUtils class containing helper methods
 */
public final class PaymentUtils {

    private final static String TAG = "payment_PaymentUtils";
    
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
