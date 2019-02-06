/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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
