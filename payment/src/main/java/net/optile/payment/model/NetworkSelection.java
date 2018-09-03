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
 * Applicable network or account registration selection preference.
 */
public class NetworkSelection {
	/** The network selection indicator. */
	private Boolean selected;

	/**
	 * Gets network selection indicator.
	 *
	 * @return <code>true</code> for request to select network, <code>false</code> to deselect it.
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * Sets network selection indicator.
	 *
	 * @param selected use <code>true</code> to mark network as selected, <code>false</code> to deselect previously selected network.
	 */
	public void setSelected(final Boolean selected) {
		this.selected = selected;
	}
}
