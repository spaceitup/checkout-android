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

package net.optile.payment.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;

/**
 * This class describes HTTP methods that are valid for customer's browser redirect.
 */
public class HttpMethod {

    public final static String GET = "GET";
    public final static String POST = "POST";

    /**
     * Check if the given method is a valid http method
     *
     * @param method the http method to validate
     * @return true when valid, false otherwise
     */
    public static boolean isHttpMethod(final String method) {

        if (method != null) {
            switch (method) {
                case GET:
                case POST:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        GET,
        POST })
    public @interface Definition { }
}



