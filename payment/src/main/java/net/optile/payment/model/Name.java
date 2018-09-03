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
 * This class is designed to hold person name information.
 */
public class Name {
	/** optional */
	private String title;
	/** optional */
	private String firstName;
	/** optional */
	private String middleName;
	/** optional */
	private String lastName;
	/** optional */
	private String maidenName;

	/**
	 * Gets value of title.
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets value of title.
	 * 
	 * @param title the title to set.
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Gets value of firstName.
	 * 
	 * @return the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets value of firstName.
	 * 
	 * @param firstName the firstName to set.
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets value of middleName.
	 * 
	 * @return the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets value of middleName.
	 * 
	 * @param middleName the middleName to set.
	 */
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets value of lastName.
	 * 
	 * @return the lastName.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets value of lastName.
	 * 
	 * @param lastName the lastName to set.
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets value of maidenName.
	 * 
	 * @return the maidenName.
	 */
	public String getMaidenName() {
		return maidenName;
	}

	/**
	 * Sets value of maidenName.
	 * 
	 * @param maidenName the maidenName to set.
	 */
	public void setMaidenName(final String maidenName) {
		this.maidenName = maidenName;
	}
}
