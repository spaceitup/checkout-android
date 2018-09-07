/*
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

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer's account (payment information for a payment method) that has been registered (i.e. stored) at Optile.
 */
public class AccountRegistration {
    /** Simple API, always present */
    private Map<String, URL> links;
    /** Simple API, always present */
    private String code;
    /** Simple API, always present */
    private String label;
    /** Simple API, always present */
    private AccountMask maskedAccount;
    /** Indicates that this account registration is initially selected */
    private Boolean selected;
    /** IFrame height for selective native, only supplied if "iFrame" link is present. */
    private Integer iFrameHeight;
    /** An indicator that a form for this network is an empty one, without any text and input elements */
    private Boolean emptyForm;
    /** Form elements descriptions */
    private List<InputElement> localizedInputElements;
    /** contract data of first possible route. */
    private Map<String, String> contractData;

    /**
     * Gets value of links.
     *
     * @return the links.
     */
    public Map<String, URL> getLinks() {
        return links;
    }

    /**
     * Sets value of links.
     *
     * @param links the links to set.
     */
    public void setLinks(final Map<String, URL> links) {
        this.links = links;
    }

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
     * Gets value of label.
     *
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets value of label.
     *
     * @param label the label to set.
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Gets value of maskedAccount.
     *
     * @return Masked (i.e. incomplete) information about this account that can be used to identify the account to the customer without         giving away sensitive information.
     */
    public AccountMask getMaskedAccount() {
        return maskedAccount;
    }

    /**
     * Sets value of maskedAccount.
     *
     * @param maskedAccount Masked (i.e. incomplete) information about this account that can be used to identify the account to the customer            without giving away sensitive information.
     */
    public void setMaskedAccount(final AccountMask maskedAccount) {
        this.maskedAccount = maskedAccount;
    }

    /**
     * Gets indication of this account registration selection.
     * <p>
     * Note: only one applicable network or account registration can be selected within a LIST.
     *
     * @return <code>true</code> means that this account registration is selected, <code>false</code> and <code>null</code> means it is not.
     * @see ApplicableNetwork#getSelected() ApplicableNetwork#getSelected()
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Sets indication of account registration selection.
     *
     * @param selected <code>true</code> means that this account registration is selected, <code>false</code> and <code>null</code> means it            is not.
     */
    public void setSelected(final Boolean selected) {
        this.selected = selected;
    }

    /**
     * Gets IFrame height for selective native integration, only supplied if "iFrame" link is present.
     *
     * @return the IFrame height in pixels.
     */
    public Integer getiFrameHeight() {
        return iFrameHeight;
    }

    /**
     * Sets IFrame height for selective native integration, only supplied if "iFrame" link is present.
     *
     * @param iFrameHeight the IFrame height in pixels.
     */
    public void setiFrameHeight(final Integer iFrameHeight) {
        this.iFrameHeight = iFrameHeight;
    }

    /**
     * Gets an indicator that this network operates with an empty form.
     *
     * @return <code>true</code> for empty form, otherwise network form contains some elements.
     */
    public Boolean getEmptyForm() {
        return emptyForm;
    }

    /**
     * Sets an indicator that this network operates with an empty form.
     *
     * @param emptyForm <code>true</code> for empty form, otherwise network form contains some elements.
     */
    public void setEmptyForm(final Boolean emptyForm) {
        this.emptyForm = emptyForm;
    }

    /**
     * Gets localized form elements.
     *
     * @return Form elements.
     */
    public java.util.List<InputElement> getLocalizedInputElements() {
        return localizedInputElements;
    }

    /**
     * Sets localized form elements.
     *
     * @param localizedInputElements Form elements.
     */
    public void setLocalizedInputElements(final List<InputElement> localizedInputElements) {
        this.localizedInputElements = localizedInputElements;
    }

    /**
     * Gets contract's public data of the first possible route which will be taken for the payment attempt.
     *
     * @return Contract 's public data of the first possible route.
     */
    public Map<String, String> getContractData() {
        return contractData;
    }

    /**
     * Sets contract's public data of the first possible route which will be taken for the payment attempt.
     *
     * @param contractData Contract's public data of the first possible route.
     */
    public void setContractData(final Map<String, String> contractData) {
        this.contractData = contractData;
    }
}
