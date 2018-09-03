/**
 * Copyright(c) 2009-2018 optile GmbH. All Rights Reserved
 * https://www.optile.net
 *
 * This software is the property of optile GmbH.  Distribution 
 * of this software without agreement in writing is strictly prohibited.
 * 
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.model;

/**
 * This class is designed to hold advanced reference information about payment.
 */
public class LongReference {
	/** mandatory (max 32) */
	private String essential;
	/** optional (max 32) */
	private String extended;
	/** optional (max 32) */
	private String verbose;

	/**
	 * Gets value of essential.
	 * 
	 * @return the essential.
	 */
	public String getEssential() {
		return essential;
	}

	/**
	 * Sets value of essential.
	 * 
	 * @param essential the essential to set.
	 */
	public void setEssential(final String essential) {
		this.essential = essential;
	}

	/**
	 * Gets value of extended.
	 * 
	 * @return the extended.
	 */
	public String getExtended() {
		return extended;
	}

	/**
	 * Sets value of extended.
	 * 
	 * @param extended the extended to set.
	 */
	public void setExtended(final String extended) {
		this.extended = extended;
	}

	/**
	 * Gets value of verbose.
	 * 
	 * @return the verbose.
	 */
	public String getVerbose() {
		return verbose;
	}

	/**
	 * Sets value of verbose.
	 * 
	 * @param verbose the verbose to set.
	 */
	public void setVerbose(final String verbose) {
		this.verbose = verbose;
	}
}
