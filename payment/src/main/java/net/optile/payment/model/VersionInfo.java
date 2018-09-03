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
 * Contains information about model version implemented by this package.
 */
public interface VersionInfo {
	/** The MimeType for payment API, enterprise representation, JSON format. */
	String JSON = "application/json;charset=UTF-8";
	/** The MimeType for payment API, enterprise representation, XML format. */
	String XML = "application/xml;charset=UTF-8";
}
