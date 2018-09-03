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

import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to hold account validation result (merchant specific and localized).
 */
public class ValidationResult {
	private boolean valid;
	private Map<String, Message> messages = new HashMap<String, Message>();

	/**
	 * Gets value of valid.
	 * 
	 * @return the valid.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Sets value of valid.
	 * 
	 * @param valid the valid to set.
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Gets value of messages.
	 * 
	 * @return the messages.
	 */
	public Map<String, Message> getMessages() {
		return messages;
	}

	/**
	 * Sets value of messages.
	 * 
	 * @param messages the messages to set.
	 */
	public void setMessages(Map<String, Message> messages) {
		this.messages = messages;
	}
}
