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

import java.util.Map;

/**
 * This class is designed to hold information for operation (CHARGE, PAYOUT, UPDATE) with selected payment network.
 */
public class OperationData {
	/** Simple API, optional */
	private AccountInputData account;
	/** Simple API, optional */
	private Boolean autoRegistration;
	/** Simple API, optional */
	private Boolean allowRecurrence;
	/** Advanced API, optional */
	private Map<String, Boolean> checkboxes;
	/** Provider request parameters. */
	private ProviderParameters providerRequest;

	/**
	 * Gets account-form input data.
	 *
	 * @return Account-form input data.
	 */
	public AccountInputData getAccount() {
		return account;
	}

	/**
	 * Sets account-form input data.
	 *
	 * @param account Account-form input data.
	 */
	public void setAccount(final AccountInputData account) {
		this.account = account;
	}

	/**
	 * Gets value of autoRegistration.
	 *
	 * @return the autoRegistration.
	 */
	public Boolean getAutoRegistration() {
		return autoRegistration;
	}

	/**
	 * Sets value of autoRegistration.
	 *
	 * @param autoRegistration the autoRegistration to set.
	 */
	public void setAutoRegistration(final Boolean autoRegistration) {
		this.autoRegistration = autoRegistration;
	}

	/**
	 * Gets value of allowRecurrence.
	 *
	 * @return the allowRecurrence.
	 */
	public Boolean getAllowRecurrence() {
		return allowRecurrence;
	}

	/**
	 * Sets value of allowRecurrence.
	 *
	 * @param allowRecurrence the allowRecurrence to set.
	 */
	public void setAllowRecurrence(final Boolean allowRecurrence) {
		this.allowRecurrence = allowRecurrence;
	}

	/**
	 * Gets value of checkboxes.
	 *
	 * @return the checkboxes.
	 */
	public Map<String, Boolean> getCheckboxes() {
		return checkboxes;
	}

	/**
	 * Sets value of checkboxes.
	 *
	 * @param checkboxes the checkboxes to set.
	 */
	public void setCheckboxes(final Map<String, Boolean> checkboxes) {
		this.checkboxes = checkboxes;
	}

	/**
	 * Gets provider request parameters.
	 *
	 * @return Provider request parameters.
	 */
	public ProviderParameters getProviderRequest() {
		return providerRequest;
	}

	/**
	 * Sets provider request parameters.
	 *
	 * @param providerRequest Provider request parameters.
	 */
	public void setProviderRequest(final ProviderParameters providerRequest) {
		this.providerRequest = providerRequest;
	}
}
