/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * Account data what should be used to pre-fill payment form.
 */
public class AccountFormData {

    /** holder name */
    private String holderName;

    /**
     * Gets value of holderName.
     *
     * @return the holderName.
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * Sets value of holderName.
     *
     * @param holderName the holderName to set.
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
}
