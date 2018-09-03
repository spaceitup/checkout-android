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

import java.util.List;

/**
 * This class holds the customers shipping, residential and billing addresses.
 */
public class CustomerAddresses {
	private Address shipping;
	private Address residential;
	private Address billing;
	private Boolean useBillingAsShippingAddress;
	/** optional, additional addresses */
	private List<Address> additionalAddresses;

	/**
	 * @return the shipping
	 */
	public Address getShipping() {
		return shipping;
	}

	/**
	 * @param shipping the shipping to set
	 */
	public void setShipping(final Address shipping) {
		this.shipping = shipping;
	}

	/**
	 * @return the residential
	 */
	public Address getResidential() {
		return residential;
	}

	/**
	 * @param residential the residential to set
	 */
	public void setResidential(final Address residential) {
		this.residential = residential;
	}

	/**
	 * @return the billing
	 */
	public Address getBilling() {
		return billing;
	}

	/**
	 * @param billing the billing to set
	 */
	public void setBilling(final Address billing) {
		this.billing = billing;
	}

	/**
	 * Sets a flag that billing address should use as shipping address if it is not provided.
	 *
	 * @param useBillingAsShippingAddress <code>true</code> to use billing as shipping address.
	 */
	public void setUseBillingAsShippingAddress(final Boolean useBillingAsShippingAddress) {
		this.useBillingAsShippingAddress = useBillingAsShippingAddress;
	}

	/**
	 * Gets a flag that billing address should use as shipping address if it is not provided.
	 *
	 * @return Set value.
	 */
	public Boolean getUseBillingAsShippingAddress() {
		return useBillingAsShippingAddress;
	}

	/**
	 * Sets a list of additional addresses.
	 *
	 * @param additionalAddresses A collection of additional addresses.
	 */
	public void setAdditionalAddresses(final List<Address> additionalAddresses) {
		this.additionalAddresses = additionalAddresses;
	}

	/**
	 * Gets a list of additional addresses.
	 *
	 * @return A collection of additional addresses.
	 */
	public List<Address> getAdditionalAddresses() {
		return additionalAddresses;
	}
}
