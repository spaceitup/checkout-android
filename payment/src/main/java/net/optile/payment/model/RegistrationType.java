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
 * This class describes registration type behavior for applicable network.
 */
public class RegistrationType {

    public final static String NONE = "NONE";
    public final static String OPTIONAL = "OPTIONAL";
    public final static String FORCED = "FORCED";
    public final static String OPTIONAL_PRESELECTED = "OPTIONAL_PRESELECTED";
    public final static String FORCED_DISPLAYED = "FORCED_DISPLAYED";
    public final static String INVALID_VALUE = "InvalidValue"; 
    
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ NONE,
            OPTIONAL,
            FORCED,
            OPTIONAL_PRESELECTED,
            FORCED_DISPLAYED })
            public @interface Definition {}

    /**
     * Gets registration type as a checked value.
     * If the value does not match any predefined types then return 
     * INVALID_VALUE
     *
     * @return the checked registration type
     */
    public static String getCheckedRegistrationType(String type) {

        if (this.type != null) {
            switch (this.type) {
                case NONE:
                case OPTIONAL:
                case FORCED:
                case OPTIONAL_PRESELECTED:
                case FORCED_DISPLAYED:
                    return this.registration;
            }
        }
        return INVALID_VALUE;
    }
}



