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

import java.math.BigDecimal;

/**
 * Payment amount data.
 */
public class PaymentAmount {
    /** amount */
    private BigDecimal amount;
    /** currency */
    private String currency;

    /**
     * Gets payment amount.
     *
     * @return Amount value in major units.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets payment amount in major units.
     *
     * @param amount Amount value in major units.
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets payment currency.
     *
     * @return Payment currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets payment currency.
     *
     * @param currency Payment currency.
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }
}
