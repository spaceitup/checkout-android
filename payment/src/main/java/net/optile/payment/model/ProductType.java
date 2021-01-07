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
 * A type of the product.
 */
public class ProductType {

    public final static String PHYSICAL = "PHYSICAL";
    public final static String DIGITAL = "DIGITAL";
    public final static String SERVICE = "SERVICE";
    public final static String TAX = "TAX";
    public final static String OTHER = "OTHER";

    /**
     * Check if the given type is a valid product type
     *
     * @param productType the product type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String productType) {

        if (productType != null) {
            switch (productType) {
                case PHYSICAL:
                case DIGITAL:
                case SERVICE:
                case TAX:
                case OTHER:
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
        PHYSICAL,
        DIGITAL,
        SERVICE,
        TAX,
        OTHER })
    public @interface Definition { }
}
