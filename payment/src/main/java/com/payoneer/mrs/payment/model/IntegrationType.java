/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * This class describes the different integration types
 */
public class IntegrationType {

    public final static String MOBILE_NATIVE = "MOBILE_NATIVE";
    public final static String DISPLAY_NATIVE = "DISPLAY_NATIVE";
    public final static String SELECTIVE_NATIVE = "SELECTIVE_NATIVE";
    public final static String PURE_NATIVE = "PURE_NATIVE";
    public final static String HOSTED = "HOSTED";

    /**
     * Check if the given type is a valid integration type
     *
     * @param integrationType the integration type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String integrationType) {

        if (integrationType != null) {
            switch (integrationType) {
                case MOBILE_NATIVE:
                case DISPLAY_NATIVE:
                case SELECTIVE_NATIVE:
                case PURE_NATIVE:
                case HOSTED:
                    return true;
            }
        }
        return false;
    }

    /**
     * The interface Definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        MOBILE_NATIVE,
        DISPLAY_NATIVE,
        SELECTIVE_NATIVE,
        PURE_NATIVE,
        HOSTED
    })
    public @interface Definition { }
}
