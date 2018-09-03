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

/**
 * This enumeration describes possible payment methods - one of the way to group payment networks.
 */
public enum PaymentMethod {
	BANK_TRANSFER,
	BILLING_PROVIDER,
	CASH_ON_DELIVERY,
	CHECK_PAYMENT,
	CREDIT_CARD,
	DEBIT_CARD,
	DIRECT_DEBIT,
	ELECTRONIC_INVOICE,
	GIFT_CARD,
	MOBILE_PAYMENT,
	ONLINE_BANK_TRANSFER,
	OPEN_INVOICE,
	PREPAID_CARD,
	TERMINAL,
	WALLET;
}
