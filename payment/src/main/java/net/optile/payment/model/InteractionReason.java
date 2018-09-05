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
 * This enumeration describes the interaction reason of a result.
 */
public enum InteractionReason {
	OK,
	PENDING,
	@Deprecated
	TRUSTED,
	STRONG_AUTHENTICATION,
	DECLINED,
	@Deprecated
	EXPIRED,
	EXCEEDS_LIMIT,
	TEMPORARY_FAILURE,
	@Deprecated
	UNKNOWN,
	NETWORK_FAILURE,
	BLACKLISTED,
	BLOCKED,
	SYSTEM_FAILURE,
	INVALID_ACCOUNT,
	FRAUD,
	ADDITIONAL_NETWORKS,
	INVALID_REQUEST,
	SCHEDULED,
	NO_NETWORKS,
	DUPLICATE_OPERATION,
	CHARGEBACK,
	RISK_DETECTED,
	CUSTOMER_ABORT,
	EXPIRED_SESSION,
	EXPIRED_ACCOUNT,
	ACCOUNT_NOT_ACTIVATED,
	TRUSTED_CUSTOMER,
	UNKNOWN_CUSTOMER,
	ACTIVATED,
	UPDATED,
	TAKE_ACTION
}
