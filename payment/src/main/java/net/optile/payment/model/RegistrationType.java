/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * This class describes registration type behavior for applicable network.
 */
public class RegistrationType {

    public final static String NONE = "NONE";
    public final static String OPTIONAL = "OPTIONAL";
    public final static String FORCED = "FORCED";
    public final static String OPTIONAL_PRESELECTED = "OPTIONAL_PRESELECTED";
    public final static String FORCED_DISPLAYED = "FORCED_DISPLAYED";

    /**
     * Check if the given type is a valid registration type
     *
     * @param type the registration type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String type) {

        if (type != null) {
            switch (type) {
                case NONE:
                case OPTIONAL:
                case FORCED:
                case OPTIONAL_PRESELECTED:
                case FORCED_DISPLAYED:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        NONE,
        OPTIONAL,
        FORCED,
        OPTIONAL_PRESELECTED,
        FORCED_DISPLAYED })
    public @interface Definition { }
}



