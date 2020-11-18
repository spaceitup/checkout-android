/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.util.List;

/**
 * This class is designed to hold information about extra elements that should be displayed on payment page.
 */
public class ExtraElements {
    private List<ExtraElement> top;
    private List<ExtraElement> bottom;

    /**
     * Gets list of top extra elements, those should be displayed above the payment methods.
     *
     * @return the top elements.
     */
    public List<ExtraElement> getTop() {
        return top;
    }

    /**
     * Sets list of top extra elements, those should be displayed above the payment methods.
     *
     * @param top the top elements to set.
     */
    public void setTop(List<ExtraElement> top) {
        this.top = top;
    }

    /**
     * Gets list of bottom extra elements, those should be displayed below the payment methods.
     *
     * @return the bottom elements.
     */
    public List<ExtraElement> getBottom() {
        return bottom;
    }

    /**
     * Sets list of bottom extra elements, those should be displayed below the payment methods.
     *
     * @param bottom the bottom elements to set.
     */
    public void setBottom(List<ExtraElement> bottom) {
        this.bottom = bottom;
    }
}
