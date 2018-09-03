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
 * This class is designed to hold address information.
 */
public class Address {
	/** optional */
	private String street;
	/** optional */
	private String houseNumber;
	/** optional */
	private String zip;
	/** optional */
	private String city;
	/** optional */
	private String state;
	/** optional */
	private String country;
	/** optional */
	private Name name;
	/** optional */
	private String companyName;
	/** optional, address id. */
	private String id;
	/** an address managed by merchant (like store/shop address) */
	private Boolean merchantAddress;

	/**
	 * Gets value of street.
	 *
	 * @return the street.
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets value of street.
	 *
	 * @param street the street to set.
	 */
	public void setStreet(final String street) {
		this.street = street;
	}

	/**
	 * Gets value of houseNumber.
	 *
	 * @return the houseNumber.
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Sets value of houseNumber.
	 *
	 * @param houseNumber the houseNumber to set.
	 */
	public void setHouseNumber(final String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * Gets value of zip.
	 *
	 * @return the zip.
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets value of zip.
	 *
	 * @param zip the zip to set.
	 */
	public void setZip(final String zip) {
		this.zip = zip;
	}

	/**
	 * Gets value of city.
	 *
	 * @return the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets value of city.
	 *
	 * @param city the city to set.
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * Gets value of state.
	 *
	 * @return the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets value of state.
	 *
	 * @param state the state to set.
	 */
	public void setState(final String state) {
		this.state = state;
	}

	/**
	 * Gets value of country.
	 *
	 * @return the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets value of country.
	 *
	 * @param country the country to set.
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * Gets value of companyName.
	 *
	 * @return the companyName.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets value of companyName.
	 *
	 * @param companyName the companyName to set.
	 */
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
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
	 * Gets address id.
	 *
	 * @return Address id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets address id.
	 *
	 * @param id Address id.
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Gets an information that this address is managed by merchant (as shop/store address).
	 *
	 * @return {@code true} for managed by merchant address.
	 */
	public Boolean getMerchantAddress() {
		return merchantAddress;
	}

	/**
	 * Sets that this address is managed by merchant (as shop/store address).
	 *
	 * @param merchantAddress {@code true} for managed by merchant address.
	 */
	public void setMerchantAddress(final Boolean merchantAddress) {
		this.merchantAddress = merchantAddress;
	}
}
