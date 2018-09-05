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
 * This class is designed to hold information about extra elements that should be displayed on payment page.
 */
public class ExtraElements {
	private List<ExtraElement> top;
	private List<ExtraElement> bottom;

	/**
	 * Gets list of top extra elements, those should be displayed above the payment methods.
	 * 
	 * @return the top elements.
	 */
	public List<ExtraElement> getTop() {
		return top;
	}

	/**
	 * Sets list of top extra elements, those should be displayed above the payment methods.
	 * 
	 * @param top the top elements to set.
	 */
	public void setTop(List<ExtraElement> top) {
		this.top = top;
	}

	/**
	 * Gets list of bottom extra elements, those should be displayed below the payment methods.
	 * 
	 * @return the bottom elements.
	 */
	public List<ExtraElement> getBottom() {
		return bottom;
	}

	/**
	 * Sets list of bottom extra elements, those should be displayed below the payment methods.
	 * 
	 * @param bottom the bottom elements to set.
	 */
	public void setBottom(List<ExtraElement> bottom) {
		this.bottom = bottom;
	}
}
