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
import java.util.List;

import android.content.res.Resources;
import android.support.v4.widget.TextViewCompat;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;

/**
 * The ResourceLoader class containing helper methods for loading network groups and validation groups
 */
public final class ResourceLoader {

    public static List<NetworkGroup> loadNetworkGroups(Resources res, int resId) throws PaymentException {
    }

    public static List<ValidationGroup> loadValidationGroups(Resources res, int resId) throws PaymentException {
    }
        
    /**
     * Read the contents of the raw resource
     *
     * @param res The system Resources
     * @param resId The resource id
     * @return The String or an empty string if something went wrong
     */
    public static String readRawResource(Resources res, int resId) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        try (InputStream is = res.openRawResource(resId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr)) {

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Resources.NotFoundException e) {
            throw new IOException("Resource not found: " + resId);
        }
        return sb.toString();
    }
}
