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
 * Holds zero or up to 7 different phone numbers with a predefined, static type.
 */
public class Phones {
	private PhoneNumber home;
	private PhoneNumber work;
	private PhoneNumber mobile;
	private PhoneNumber mobileSecondary;
	private PhoneNumber company;
	private PhoneNumber fax;
	private PhoneNumber other;

	/**
	 * @return the home (landline) number
	 */
	public PhoneNumber getHome() {
		return home;
	}

	/**
	 * @param home the home (landline) number
	 */
	public void setHome(final PhoneNumber home) {
		this.home = home;
	}

	/**
	 * @return the work number
	 */
	public PhoneNumber getWork() {
		return work;
	}

	/**
	 * @param work the work number
	 */
	public void setWork(final PhoneNumber work) {
		this.work = work;
	}

	/**
	 * @return the mobile number
	 */
	public PhoneNumber getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile number
	 */
	public void setMobile(final PhoneNumber mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the secondary (alternative) mobile number
	 */
	public PhoneNumber getMobileSecondary() {
		return mobileSecondary;
	}

	/**
	 * @param mobileSecondary the secondary (alternative) mobile number
	 */
	public void setMobileSecondary(final PhoneNumber mobileSecondary) {
		this.mobileSecondary = mobileSecondary;
	}

	/**
	 * @return the number of the user's company front office
	 */
	public PhoneNumber getCompany() {
		return company;
	}

	/**
	 * @param company the number of the user's company front office
	 */
	public void setCompany(final PhoneNumber company) {
		this.company = company;
	}

	/**
	 * @return the fax number
	 */
	public PhoneNumber getFax() {
		return fax;
	}

	/**
	 * @param fax the fax number
	 */
	public void setFax(final PhoneNumber fax) {
		this.fax = fax;
	}

	/**
	 * @return an arbitrary number that might not fit in another category
	 */
	public PhoneNumber getOther() {
		return other;
	}

	/**
	 * @param other an arbitrary number that might not fit in another category
	 */
	public void setOther(final PhoneNumber other) {
		this.other = other;
	}
}
