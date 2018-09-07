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

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class is designed to hold information checkbox element that is displayed on payment page.
 */
public class Checkbox {

    /**
     * The constant MODE_OPTIONAL.
     */
    public final static String MODE_OPTIONAL = "OPTIONAL";
    /**
     * The constant MODE_OPTIONAL_PRESELECTED.
     */
    public final static String MODE_OPTIONAL_PRESELECTED = "OPTIONAL_PRESELECTED";
    /**
     * The constant MODE_REQUIRED.
     */
    public final static String MODE_REQUIRED = "REQUIRED";
    /**
     * The constant MODE_REQUIRED_PRESELECTED.
     */
    public final static String MODE_REQUIRED_PRESELECTED = "REQUIRED_PRESELECTED";
    /**
     * The constant MODE_FORCED.
     */
    public final static String MODE_FORCED = "FORCED";
    /**
     * The constant MODE_FORCED_DISPLAYED.
     */
    public final static String MODE_FORCED_DISPLAYED = "FORCED_DISPLAYED";
    /**
     * The constant MODE_UNKNOWN.
     */
    public final static String MODE_UNKNOWN = "UnknownMode";
    /**
     * Advanced API, required
     */
    private String name;
    /**
     * Advanced API, required
     */
    @CheckboxMode
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
    @CheckboxMode
    public String getMode() {
        return mode;
    }

    /**
     * Sets mode of displayed checkbox.
     *
     * @param mode the mode to set.
     */
    public void setMode(@CheckboxMode String mode) {
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

    /**
     * Gets mode of displayed checkbox as a checked value.
     * If the value does not match any predefined modes then return
     * MODE_UNKNOWN.
     *
     * @return the mode.
     */
    @CheckboxMode
    public String getCheckedMode() {

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
        return MODE_UNKNOWN;
    }

    /**
     * The interface Checkbox mode.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MODE_OPTIONAL,
            MODE_OPTIONAL_PRESELECTED,
            MODE_REQUIRED,
            MODE_REQUIRED_PRESELECTED,
            MODE_FORCED,
            MODE_FORCED_DISPLAYED,
            MODE_UNKNOWN})
    public @interface CheckboxMode {
    }
}
