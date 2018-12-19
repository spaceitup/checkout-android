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
 * This class describes the different Operation types.
 */
public class OperationType {

    public final static String CHARGE = "CHARGE";
    public final static String PRESET = "PRESET";
    public final static String PAYOUT = "PAYOUT";
    public final static String UPDATE = "UPDATE";

    /**
     * Check if the given type is a valid operation type
     *
     * @param type the operation type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String type) {

        if (type != null) {
            switch (type) {
                case CHARGE:
                case PRESET:
                case PAYOUT:
                case UPDATE:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        CHARGE,
        PRESET,
        PAYOUT,
        UPDATE })
    public @interface Definition { }
}



