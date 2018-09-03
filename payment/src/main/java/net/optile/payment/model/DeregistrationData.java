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
 * An information about deregistration options.
 */
public class DeregistrationData {
	/** Simple API, optional - deregister one-click registrations. */
	private Boolean deleteRegistration;
	/** Simple API, optional - deregister recurring registrations. */
	private Boolean deleteRecurrence;

	/**
	 * Gets intention to delete one-click registrations.
	 * 
	 * @return <code>true</code> if one-click registrations must be deleted.
	 */
	public Boolean getDeleteRegistration() {
		return deleteRegistration;
	}

	/**
	 * Sets an information to delete one-click registrations or don't.
	 * 
	 * @param deleteRegistration <code>true</code> to delete one-click registrations.
	 */
	public void setDeleteRegistration(final Boolean deleteRegistration) {
		this.deleteRegistration = deleteRegistration;
	}

	/**
	 * Gets intention to delete recurring registrations.
	 * 
	 * @return <code>true</code> if recurring registrations must be deleted.
	 */
	public Boolean getDeleteRecurrence() {
		return deleteRecurrence;
	}

	/**
	 * Sets an information to delete recurring registrations or don't.
	 * 
	 * @param deleteRecurrence <code>true</code> to delete recurring registrations.
	 */
	public void setDeleteRecurrence(final Boolean deleteRecurrence) {
		this.deleteRecurrence = deleteRecurrence;
	}
}
