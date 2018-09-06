/**
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

import java.util.List;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;

/**
 * Form input element description.
 */
public class InputElement {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ TYPE_STRING,
            TYPE_NUMERIC,
            TYPE_INTEGER,
            TYPE_SELECT,
            TYPE_CHECKBOX,
            TYPE_UNKNOWN })
            public @interface InputElementType {}

    /**
     * One line of text without special restrictions (example: holder name)
     */
    public final static String TYPE_STRING = "string";

    /**
     * Numbers 0-9 and the delimiters space and dash ('-') are allowed (example: card numbers)
     */
    public final static String TYPE_NUMERIC = "numeric";

    /**
     * Numbers 0-9 only (example: CVC)
     */
    public final static String TYPE_INTEGER = "integer";

    /**
     * A list of possible values is given in an additional options attribute
     */
    public final static String TYPE_SELECT = "select";

    /**
     * Checkbox type, what allows 'true' for set and 'null' or 'false' for non-set values
     */
    public final static String TYPE_CHECKBOX = "checkbox";

    /**
     * The unknown type
     */
    public final static String TYPE_UNKNOWN = "Unknown";

    /** name */
    private String name;
    /** type */
    private String type;
    /** localized label */
    private String label;
    /** options */
    private List<SelectOption> options;

    /**
     * Gets name.
     *
     * @return A name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name A name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return A type value.
     */
    @InputElementType
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type A type value.
     */
    public void setType(@InputElementType String type) {
        this.type = type;
    }

    /**
     * Gets localized label.
     *
     * @return A localized label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets localized label.
     *
     * @param label A localized label.
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Gets options.
     *
     * @return Non-empty list of options.
     */
    public List<SelectOption> getOptions() {
        return options;
    }

    /**
     * Sets options.
     *
     * @param options Non-empty list of options.
     */
    public void setOptions(final List<SelectOption> options) {
        this.options = options;
    }

    /**
     * Gets type as a checked value.
     * If the value does not match any predefined modes then return
     * TYPE_UNKNOWN.
     *
     * @return the checked type
     */
    @InputElementType
    public String getCheckedType() {

        if (this.type != null) {
            switch (this.type) {
            case TYPE_STRING:
            case TYPE_NUMERIC:
            case TYPE_INTEGER:
            case TYPE_SELECT:
            case TYPE_CHECKBOX:
                return this.type;
            }
        }
        return TYPE_UNKNOWN;
    }
}
