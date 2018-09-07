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
    private String label;
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
     * Gets label of displayed checkbox.
     *
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label of displayed checkbox.
     *
     * @param label the label to set.
     */
    public void setLabel(String label) {
        this.label = label;
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
