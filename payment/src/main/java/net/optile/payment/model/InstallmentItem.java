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
