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
 * This class is designed to hold information about extra element that is displayed on payment page.
 */
public class ExtraElement {
    /** Advanced API, optional */
    private String text;
    /** Advanced API, optional */
    private Checkbox checkbox;

    /**
     * Gets value of description text to display.
     *
     * @return the text to display.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets value of description text to display.
     *
     * @param text the text to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets information about checkbox to display.
     *
     * @return the checkbox or <code>null</code> if no checkbox required.
     */
    public Checkbox getCheckbox() {
        return checkbox;
    }

    /**
     * Sets information about checkbox to display.
     *
     * @param checkbox the checkbox information to set (may be <code>null</code>).
     */
    public void setCheckbox(Checkbox checkbox) {
        this.checkbox = checkbox;
    }
}
