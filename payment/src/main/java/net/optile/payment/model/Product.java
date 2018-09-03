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

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

/**
 * This class is designed to hold product information.
 */
public class Product {
	/** optional */
	private String code;
	/** mandatory */
	private String name;
	/** optional (totalAmount) */
	private BigDecimal amount;
	/** optional */
	private String currency;
	/** optional */
	private Integer quantity;
	/** optional */
	private Date plannedShippingDate;
	/** optional */
	private URL productDescriptionUrl;
	/** optional */
	private URL productImageUrl;
	/** optional */
	private String description;
	/** optional */
	private String shippingAddressId;
	/** optional */
	private ProductType type;

	// TODO: shipping, subscription, addon, metering, entitlement

	/**
	 * Gets value of code.
	 *
	 * @return the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets value of code.
	 *
	 * @param code the code to set.
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Gets value of name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets value of name.
	 *
	 * @param name the name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets value of amount.
	 *
	 * @return the amount.
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets value of amount.
	 *
	 * @param amount the amount to set.
	 */
	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets value of currency.
	 *
	 * @return the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets value of currency.
	 *
	 * @param currency the currency to set.
	 */
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets planned shipping date.
	 *
	 * @param plannedShippingDate planned shipping date.
	 */
	public void setPlannedShippingDate(final Date plannedShippingDate) {
		this.plannedShippingDate = plannedShippingDate;
	}

	/**
	 * Gets planned shipping date.
	 *
	 * @return planned shipping date.
	 */
	public Date getPlannedShippingDate() {
		return plannedShippingDate;
	}

	/**
	 * Gets product's image URL.
	 *
	 * @return Product's image URL.
	 */
	public URL getProductImageUrl() {
		return productImageUrl;
	}

	/**
	 * Sets product's image URL.
	 *
	 * @param productImageUrl Product's image URL.
	 */
	public void setProductImageUrl(final URL productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	/**
	 * Gets product's description URL.
	 *
	 * @return Product's description URL.
	 */
	public URL getProductDescriptionUrl() {
		return productDescriptionUrl;
	}

	/**
	 * Sets product's description URL.
	 *
	 * @param productDescriptionUrl Product's description URL.
	 */
	public void setProductDescriptionUrl(final URL productDescriptionUrl) {
		this.productDescriptionUrl = productDescriptionUrl;
	}

	/**
	 * Sets product description.
	 *
	 * @param description Product description.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets product description.
	 *
	 * @return Product description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets an ID of shipping address.
	 *
	 * @return Shipping address ID.
	 */
	public String getShippingAddressId() {
		return shippingAddressId;
	}

	/**
	 * Sets an ID of shipping address.
	 *
	 * @param shippingAddressId Shipping address ID.
	 */
	public void setShippingAddressId(final String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	/**
	 * Gets product type.
	 *
	 * @return Product type.
	 */
	public ProductType getType() {
		return type;
	}

	/**
	 * Sets product type.
	 *
	 * @param type Product's type.
	 */
	public void setType(final ProductType type) {
		this.type = type;
	}
}
