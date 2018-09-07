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

/**
 * Option description.
 */
public class SelectOption {
    /** value */
    private String value;
    /** label */
    private String label;
    /** a flag for the option to be preselected - shown first in the drop-down list */
    private Boolean selected;

    /**
     * Gets label.
     *
     * @return Label text if it is specified or {@code null}.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label Label text.
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Gets value.
     *
     * @return Value text.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value Value text.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Gets a flag of pre-selected option, option what should be first in the drop-down list.
     *
     * @return {@code true} - for pre-selected option, {@code null} - for usual, not pre-selected option.
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Sets 'selected' flag.
     *
     * @param selected {@code true} - for pre-selected option, {@code null} - for usual, not pre-selected option.
     */
    public void setSelected(final Boolean selected) {
        this.selected = selected;
    }
}
