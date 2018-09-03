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
 * Input data what could been submitted by payment-page form.
 */
public class AccountInputData {
	/** Simple API, optional */
	private String holderName;
	/** Simple API, optional */
	private String number;
	/** Simple API, optional */
	private String bankCode;
	/** Simple API, optional */
	private String bankName;
	/** Simple API, optional */
	private String bic;
	/** Simple API, optional */
	private String branch;
	/** Simple API, optional */
	private String city;
	/** Simple API, optional */
	private String expiryMonth;
	/** Simple API, optional */
	private String expiryYear;
	/** Simple API, optional */
	private String iban;
	/** Simple API, optional */
	private String login;
	/** Simple API, optional */
	private Boolean optIn;
	/** Simple API, optional */
	private String password;
	/** Simple API, optional */
	private String verificationCode;

	/** day of customer's birthday */
	private String customerBirthDay;
	/** month of customer's birthday */
	private String customerBirthMonth;
	/** year of customer's birthday */
	private String customerBirthYear;

	/** id of installment plan */
	private String installmentPlanId;


	/**
	 * Gets a day of customer's birthday.
	 *
	 * @return Day of customer's birthday.
	 */
	public String getCustomerBirthDay() {
		return customerBirthDay;
	}

	/**
	 * Sets a day of customer's birthday.
	 *
	 * @param customerBirthDay Day of customer's birthday.
	 */
	public void setCustomerBirthDay(final String customerBirthDay) {
		this.customerBirthDay = customerBirthDay;
	}

	/**
	 * Gets month of customer's birthday.
	 *
	 * @return Month of customer's birthday.
	 */
	public String getCustomerBirthMonth() {
		return customerBirthMonth;
	}

	/**
	 * Sets month of customer's birthday.
	 *
	 * @param customerBirthMonth Month of customer's birthday.
	 */
	public void setCustomerBirthMonth(final String customerBirthMonth) {
		this.customerBirthMonth = customerBirthMonth;
	}

	/**
	 * Gets year of customer's birthday.
	 *
	 * @return Year of customer's birthday.
	 */
	public String getCustomerBirthYear() {
		return customerBirthYear;
	}

	/**
	 * Sets year of customer's birthday.
	 *
	 * @param customerBirthYear Year of customer's birthday.
	 */
	public void setCustomerBirthYear(final String customerBirthYear) {
		this.customerBirthYear = customerBirthYear;
	}

	/**
	 * Gets an id of selected installment plan.
	 *
	 * @return Id of instalment plan.
	 */
	public String getInstallmentPlanId() {
		return installmentPlanId;
	}

	/**
	 * Sets id of selected installment plan.
	 *
	 * @param installmentPlanId Id of instalment plan.
	 */
	public void setInstallmentPlanId(final String installmentPlanId) {
		this.installmentPlanId = installmentPlanId;
	}

	/**
	 * Gets value of holderName.
	 *
	 * @return the holderName.
	 */
	public String getHolderName() {
		return holderName;
	}

	/**
	 * Sets value of holderName.
	 *
	 * @param holderName the holderName to set.
	 */
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	/**
	 * Gets value of number.
	 *
	 * @return the number.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets value of number.
	 *
	 * @param number the number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets value of bankCode.
	 *
	 * @return the bankCode.
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * Sets value of bankCode.
	 *
	 * @param bankCode the bankCode to set.
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * Gets value of bankName.
	 *
	 * @return the bankName.
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets value of bankName.
	 *
	 * @param bankName the bankName to set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Gets value of bic.
	 *
	 * @return the bic.
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * Sets value of bic.
	 *
	 * @param bic the bic to set.
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * Gets value of branch.
	 *
	 * @return the branch.
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets value of branch.
	 *
	 * @param branch the branch to set.
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets value of city.
	 *
	 * @return the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets value of city.
	 *
	 * @param city the city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets value of expiryMonth.
	 *
	 * @return the expiryMonth.
	 */
	public String getExpiryMonth() {
		return expiryMonth;
	}

	/**
	 * Sets value of expiryMonth.
	 *
	 * @param expiryMonth the expiryMonth to set.
	 */
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	/**
	 * Gets value of expiryYear.
	 *
	 * @return the expiryYear.
	 */
	public String getExpiryYear() {
		return expiryYear;
	}

	/**
	 * Sets value of expiryYear.
	 *
	 * @param expiryYear the expiryYear to set.
	 */
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	/**
	 * Gets value of iban.
	 *
	 * @return the iban.
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Sets value of iban.
	 *
	 * @param iban the iban to set.
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Gets value of login.
	 *
	 * @return the login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets value of login.
	 *
	 * @param login the login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets value of optIn.
	 *
	 * @return the optIn.
	 */
	public Boolean getOptIn() {
		return optIn;
	}

	/**
	 * Sets value of optIn.
	 *
	 * @param optIn the optIn to set.
	 */
	public void setOptIn(Boolean optIn) {
		this.optIn = optIn;
	}

	/**
	 * Gets value of password.
	 *
	 * @return the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets value of password.
	 *
	 * @param password the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets value of verificationCode.
	 *
	 * @return the verificationCode.
	 */
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * Sets value of verificationCode.
	 *
	 * @param verificationCode the verificationCode to set.
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
