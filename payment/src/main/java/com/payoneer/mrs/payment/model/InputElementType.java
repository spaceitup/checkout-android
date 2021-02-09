/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Type of possible input element
 */
public class InputElementType {

    /**
     * One line of text without special restrictions (example: holder name)
     */
    public final static String STRING = "string";
    /**
     * Numbers 0-9 and the delimiters space and dash ('-') are allowed (example: card numbers)
     */
    public final static String NUMERIC = "numeric";
    /**
     * Numbers 0-9 only (example: CVC)
     */
    public final static String INTEGER = "integer";
    /**
     * A list of possible values is given in an additional options attribute
     */
    public final static String SELECT = "select";
    /**
     * Checkbox type, what allows 'true' for set and 'null' or 'false' for non-set values
     */
    public final static String CHECKBOX = "checkbox";

    /**
     * Check if the given type is a valid input element type
     *
     * @param type the input element type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String type) {

        if (type != null) {
            switch (type) {
                case STRING:
                case NUMERIC:
                case INTEGER:
                case SELECT:
                case CHECKBOX:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        STRING,
        NUMERIC,
        INTEGER,
        SELECT,
        CHECKBOX })
    public @interface Definition { }
}



