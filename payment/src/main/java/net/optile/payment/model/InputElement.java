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
 * Form input element description.
 */
public class InputElement {
	/** name */
	private String name;
	/** type */
	private InputElementType type;
	/** localized label */
	private String label;
	/** options */
	private List<SelectOption> options;

	/**
	 * Gets name.
	 *
	 * @return A name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name A name.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets type.
	 *
	 * @return A type value.
	 */
	public InputElementType getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type A type value.
	 */
	public void setType(final InputElementType type) {
		this.type = type;
	}

	/**
	 * Gets localized label.
	 *
	 * @return A localized label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets localized label.
	 *
	 * @param label A localized label.
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * Gets options.
	 *
	 * @return Non-empty list of options.
	 */
	public List<SelectOption> getOptions() {
		return options;
	}

	/**
	 * Sets options.
	 *
	 * @param options Non-empty list of options.
	 */
	public void setOptions(final List<SelectOption> options) {
		this.options = options;
	}
}
