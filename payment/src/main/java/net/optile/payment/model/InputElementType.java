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
 * Type of possible input element.
 */
public enum InputElementType {
	/** One line of text without special restrictions (example: holder name) */
	string,
	/** Numbers 0-9 and the delimiters space and dash ('-') are allowed (example: card numbers) */
	numeric,
	/** Numbers 0-9 only (example: CVC) */
	integer,
	/** A list of possible values is given in an additional options attribute */
	select,
	/** Checkbox type, what allows 'true' for set and 'null' or 'false' for non-set values */
	checkbox
}
