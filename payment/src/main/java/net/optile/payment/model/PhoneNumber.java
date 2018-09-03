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
 * Describes a customers phone number.
 */
public class PhoneNumber {
	private String countryCode;
	private String areaCode;
	private String subscriberNumber;
	private String unstructuredNumber;

	/**
	 * The <a href="http://en.wikipedia.org/wiki/List_of_country_calling_codes">country code</a> of this phone number. Must have between 1
	 * and 4 digits and must not start with a &quot;0&quot;.
	 * 
	 * @return the country code consisting of 1 up to 4 digits
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * The <a href="http://en.wikipedia.org/wiki/List_of_country_calling_codes">country code</a> of this phone number. Must have between 1
	 * and 4 digits and must not start with a &quot;0&quot;.
	 * 
	 * @param countryCode the country code consisting of 1 up to 4 digits
	 */
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * The <a href="http://en.wikipedia.org/wiki/Area_code#Area_code">area code</a> of this phone number. Must have between 1 and 6 digits
	 * and must not start with a &quot;0&quot;.
	 * 
	 * @return the area code consisting of 1 up to 6 digits
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * The <a href="http://en.wikipedia.org/wiki/Area_code#Area_code">area code</a> of this phone number. Must have between 1 and 6 digits
	 * and must not start with a &quot;0&quot;.
	 * 
	 * @param areaCode the area code consisting of 1 up to 6 digits; might be <code>null</code>
	 */
	public void setAreaCode(final String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * The subscriber number of this phone number. Must have between 3 and 9 digits.
	 * 
	 * @return the subscriber number consisting of 3 up to 9 digits
	 */
	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	/**
	 * The subscriber number of this phone number. Must have between 3 and 9 digits.
	 * 
	 * @param subscriberNumber the subscriber number consisting of 3 up to 9 digits
	 */
	public void setSubscriberNumber(final String subscriberNumber) {
		this.subscriberNumber = subscriberNumber;
	}

	/**
	 * The unstructured number, ignoring the discrete fields for {@link #getCountryCode() country code}, {@link #getAreaCode() area code}
	 * and {@link #getSubscriberNumber() subscriber number}.
	 * 
	 * @return the unstructured number
	 */
	public String getUnstructuredNumber() {
		return unstructuredNumber;
	}

	/**
	 * The unstructured number, ignoring the discrete fields for {@link #getCountryCode() country code}, {@link #getAreaCode() area code}
	 * and {@link #getSubscriberNumber() subscriber number}.
	 * 
	 * @param unstructuredNumber the unstructured number, might start with a &quot;+&quot; and contain the following characters:
	 *            &quot;(&quot;, &quot;)&quot;, &quot; &quot;, &quot;/&quot;, &quot;-&quot;
	 */
	public void setUnstructuredNumber(final String unstructuredNumber) {
		this.unstructuredNumber = unstructuredNumber;
	}
}
