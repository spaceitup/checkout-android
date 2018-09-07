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
