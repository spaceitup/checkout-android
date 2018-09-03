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

import java.util.Date;

/**
 * This class is designed to hold customer information.
 */
public class Customer {
	/** optional */
	private String number;
	/** optional, advised */
	private String email;
	/** optional */
	private Date birthday;
	/** optional */
	private Name name;
	/** optional */
	private CustomerAddresses addresses;
	/** optional */
	private Phones phones;

	/**
	 * Gets value of number.
	 *
	 * @return the number.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets value of number.
	 *
	 * @param number the number to set.
	 */
	public void setNumber(final String number) {
		this.number = number;
	}

	/**
	 * Gets value of email.
	 *
	 * @return the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets value of email.
	 *
	 * @param email the email to set.
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Gets value of birthday.
	 *
	 * @return the birthday.
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Sets value of birthday.
	 *
	 * @param birthday the birthday to set.
	 */
	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets value of name.
	 *
	 * @return the name.
	 */
	public Name getName() {
		return name;
	}

	/**
	 * Sets value of name.
	 *
	 * @param name the name to set.
	 */
	public void setName(final Name name) {
		this.name = name;
	}

	/**
	 * Gets value of addresses.
	 *
	 * @return the addresses.
	 */
	public CustomerAddresses getAddresses() {
		return addresses;
	}

	/**
	 * Sets value of addresses.
	 *
	 * @param addresses the addresses to set.
	 */
	public void setAddresses(final CustomerAddresses addresses) {
		this.addresses = addresses;
	}

	/**
	 * Gets value of phones.
	 *
	 * @return the phones.
	 */
	public Phones getPhones() {
		return phones;
	}

	/**
	 * Sets value of phones.
	 *
	 * @param phones the phones to set.
	 */
	public void setPhones(final Phones phones) {
		this.phones = phones;
	}
}
