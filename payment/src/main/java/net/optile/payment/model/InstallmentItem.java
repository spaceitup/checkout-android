/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * An information about particular payment what is involved into installment payment process.
 */
public class InstallmentItem {
    /** The amount of installment (mandatory) */
    private BigDecimal amount;
    /** Installment/payment date */
    private Date date;

    /**
     * Gets installment amount.
     *
     * @return Amount in major units.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets installment amount.
     *
     * @param amount Amount in major units.
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets installment date.
     *
     * @return Installment (or payment) date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets installment date.
     *
     * @param date Installment date.
     */
    public void setDate(final Date date) {
        this.date = date;
    }
}
