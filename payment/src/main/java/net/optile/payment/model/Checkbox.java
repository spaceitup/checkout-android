/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold information checkbox element that is displayed on payment page.
 */
public class Checkbox {

    /**
     * Advanced API, required
     */
    private String name;
    /**
     * Advanced API, required
     */
    @CheckboxMode.Definition
    private String mode;
    /**
     * Advanced API, optional
     */
    private String requireMsg;

    /**
     * Gets name of displayed checkbox.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of displayed checkbox.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets mode of displayed checkbox.
     *
     * @return the mode.
     */
    @CheckboxMode.Definition
    public String getMode() {
        return mode;
    }

    /**
     * Sets mode of displayed checkbox.
     *
     * @param mode the mode to set.
     */
    public void setMode(@CheckboxMode.Definition String mode) {
        this.mode = mode;
    }

    /**
     * Gets message that should be displayed if required checkbox is not selected.
     *
     * @return the require message.
     */
    public String getRequireMsg() {
        return requireMsg;
    }

    /**
     * Sets message that should be displayed if required checkbox is not selected.
     *
     * @param requireMsg the require message to set.
     */
    public void setRequireMsg(String requireMsg) {
        this.requireMsg = requireMsg;
    }
}
