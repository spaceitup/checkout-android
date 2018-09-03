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

/**
 * This class is designed to hold payment information.
 */
public class Payment {
	/** mandatory */
	private String reference;
	/** mandatory */
	private BigDecimal amount;
	/** mandatory */
	private String currency;
	/** optional (max 128) */
	private String invoiceId;
	/** optional */
	private LongReference longReference;

	/**
	 * Gets value of reference.
	 * 
	 * @return the reference.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets value of reference.
	 * 
	 * @param reference the reference to set.
	 */
	public void setReference(final String reference) {
		this.reference = reference;
	}

	/**
	 * Gets value of amount.
	 * 
	 * @return the amount.
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets value of amount.
	 * 
	 * @param amount the amount to set.
	 */
	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets value of currency.
	 * 
	 * @return the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets value of currency.
	 * 
	 * @param currency the currency to set.
	 */
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	/**
	 * Gets value of invoiceId.
	 * 
	 * @return the invoiceId.
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * Sets value of invoiceId.
	 * 
	 * @param invoiceId the invoiceId to set.
	 */
	public void setInvoiceId(final String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * Gets value of longReference.
	 * 
	 * @return the longReference.
	 */
	public LongReference getLongReference() {
		return longReference;
	}

	/**
	 * Sets value of longReference.
	 * 
	 * @param longReference the longReference to set.
	 */
	public void setLongReference(final LongReference longReference) {
		this.longReference = longReference;
	}
}
