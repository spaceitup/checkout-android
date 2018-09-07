/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
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
