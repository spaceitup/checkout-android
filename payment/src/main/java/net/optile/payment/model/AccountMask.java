/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold account mask for registered payment network.
 */
public class AccountMask {
    /** Simple API, always present */
    private String displayLabel;
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
    private Integer expiryMonth;
    /** Simple API, optional */
    private Integer expiryYear;
    /** Simple API, optional */
    private String iban;
    /** Simple API, optional */
    private String login;

    /**
     * Gets value of displayLabel.
     *
     * @return the displayLabel.
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

    /**
     * Sets value of displayLabel.
     *
     * @param displayLabel the displayLabel to set.
     */
    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
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
    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Sets value of expiryMonth.
     *
     * @param expiryMonth the expiryMonth to set.
     */
    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * Gets value of expiryYear.
     *
     * @return the expiryYear.
     */
    public Integer getExpiryYear() {
        return expiryYear;
    }

    /**
     * Sets value of expiryYear.
     *
     * @param expiryYear the expiryYear to set.
     */
    public void setExpiryYear(Integer expiryYear) {
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
}
