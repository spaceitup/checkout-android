/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.model;

import java.net.URL;

/**
 * Form data to pre-fill network form. Not all data could be provided- it depends what data we know already and what network should been used.
 */
public class FormData {
    /** account-related data to pre-fill a form */
    private AccountFormData account;
    /** customer-related data to pre-fill a form */
    private CustomerFormData customer;
    /** installments plans data */
    private Installments installments;
    /** An URL to the data privacy consent document */
    private URL dataPrivacyConsentUrl;

    /**
     * Gets account-related data to pre-fill a form.
     *
     * @return Account -related form data.
     */
    public AccountFormData getAccount() {
        return account;
    }

    /**
     * Sets account-related data to pre-fill a form.
     *
     * @param account Account-related form data.
     */
    public void setAccount(final AccountFormData account) {
        this.account = account;
    }

    /**
     * Gets installments data.
     *
     * @return Installments data.
     */
    public Installments getInstallments() {
        return installments;
    }

    /**
     * Sets installments data.
     *
     * @param installments Installments data.
     */
    public void setInstallments(final Installments installments) {
        this.installments = installments;
    }

    /**
     * Gets customer-related data to pre-fill a form.
     *
     * @return Customer -related data to pre-fill a form.
     */
    public CustomerFormData getCustomer() {
        return customer;
    }

    /**
     * Sets customer-related data to pre-fill a form.
     *
     * @param customer Customer-related data to pre-fill a form.
     */
    public void setCustomer(final CustomerFormData customer) {
        this.customer = customer;
    }

    /**
     * Gets an URL to the data privacy consent document.
     *
     * @return URL object.
     */
    public URL getDataPrivacyConsentUrl() {
        return dataPrivacyConsentUrl;
    }

    /**
     * Sets an URL to the data privacy consent document.
     *
     * @param dataPrivacyConsentUrl URL object.
     */
    public void setDataPrivacyConsentUrl(final URL dataPrivacyConsentUrl) {
        this.dataPrivacyConsentUrl = dataPrivacyConsentUrl;
    }
}
