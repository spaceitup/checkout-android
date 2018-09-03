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
 * This class is designed to hold simplified information to style a redirect pages for express preset transaction.
 */
public class Style {
	/** optional */
	private String language;

	/**
	 * Gets value of language.
	 * 
	 * @return the language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets value of language.
	 * 
	 * @param language the language to set.
	 */
	public void setLanguage(final String language) {
		this.language = language;
	}
}
