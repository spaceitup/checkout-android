/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold information about HTTP parameter.
 */
public class Parameter {
    /** Simple API, always present */
    private String name;
    /** Simple API, optional */
    private String value;

    /**
     * Gets value of name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets value of name.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value of value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value of value.
     *
     * @param value the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
