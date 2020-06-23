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
 * This class describes the different operation types
 */
public class OperationType {

    public final static String CHARGE = "CHARGE";
    public final static String PRESET = "PRESET";
    public final static String PAYOUT = "PAYOUT";
    public final static String UPDATE = "UPDATE";
    public final static String ACTIVATION = "ACTIVATION";

    /**
     * Check if the given type is a valid operation type
     *
     * @param operationType the operation type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String operationType) {

        if (operationType != null) {
            switch (operationType) {
                case CHARGE:
                case PRESET:
                case PAYOUT:
                case UPDATE:
                case ACTIVATION:
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
        CHARGE,
        PRESET,
        PAYOUT,
        UPDATE,
        ACTIVATION
    })
    public @interface Definition { }
}
