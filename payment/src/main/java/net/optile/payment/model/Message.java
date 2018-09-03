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

import java.util.Set;

/**
 * This class is designed to hold field validation message information.
 */
public class Message {
	private MessageType type;
	private String message;
	private Set<String> referredTo;

	/**
	 * Gets value of type.
	 * 
	 * @return the type.
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * Sets value of type.
	 * 
	 * @param type the type to set.
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * Gets value of message.
	 * 
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets value of message.
	 * 
	 * @param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets element names which this validation result refers.
	 *
	 * @param referredTo Names of referred elements.
	 */
	public void setReferredTo(final Set<String> referredTo) {
		this.referredTo = referredTo;
	}

	/**
	 * Gets element names which this validation result refers.
	 *
	 * @return Names of referred elements.
	 */
	public Set<String> getReferredTo() {
		return referredTo;
	}
}
