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
public class CheckboxMode {

    public final static String MODE_OPTIONAL = "OPTIONAL";
    public final static String MODE_OPTIONAL_PRESELECTED = "OPTIONAL_PRESELECTED";
    public final static String MODE_REQUIRED = "REQUIRED";
    public final static String MODE_REQUIRED_PRESELECTED = "REQUIRED_PRESELECTED";
    public final static String MODE_FORCED = "FORCED";
    public final static String MODE_FORCED_DISPLAYED = "FORCED_DISPLAYED";
    public final static String INVALID_VALUE = "InvalidValue";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ MODE_OPTIONAL,
        MODE_OPTIONAL_PRESELECTED,
        MODE_REQUIRED,
        MODE_REQUIRED_PRESELECTED,
        MODE_FORCED,
        MODE_FORCED_DISPLAYED,
        MODE_UNKNOWN })
    public @interface Definition {}

    /**
     * Gets mode of displayed checkbox as a checked value.
     * If the value does not match any predefined modes then return
     * INVALID_VALUE.
     *
     * @return the checked mode
     */
    public static String getCheckedMode(String mode) {

        if (this.mode != null) {
            switch (this.mode) {
                case MODE_OPTIONAL:
                case MODE_OPTIONAL_PRESELECTED:
                case MODE_REQUIRED:
                case MODE_REQUIRED_PRESELECTED:
                case MODE_FORCED:
                case MODE_FORCED_DISPLAYED:
                    return this.mode;
            }
        }
        return INVALID_VALUE;
    }
}
