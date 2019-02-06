/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * This class is designed to hold information about applicable payment network.
 */
public class ApplicableNetwork {

    /** Simple API, always present */
    private String code;
    /** Simple API, always present */
    private String label;
    /** Simple API, always present */
    @PaymentMethod.Definition
    private String method;
    /** Simple API, always present */
    private String grouping;
    /** Simple API, always present */
    @RegistrationType.Definition
    private String registration;
    /** Simple API, always present */
    @RegistrationType.Definition
    private String recurrence;
    /** Simple API, always present */
    private Boolean redirect;
    /** Simple API, always present */
    private Map<String, URL> links;
    /** code of button-label if this network is selected */
    private String button;
    /** flag that network is initially selected */
    private Boolean selected;
    /** form data to pre-fill a form */
    private FormData formData;
    /** IFrame height for selective native, only supplied if "iFrame" link is present. */
    private Integer iFrameHeight;
    /** An indicator that a form for this network is an empty one, without any text and input elements */
    private Boolean emptyForm;
    /** Form elements descriptions */
    private List<InputElement> localizedInputElements;
    /** contract data of first possible route. */
    private Map<String, String> contractData;

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
     * Gets value of method.
     *
     * @return the method.
     */
    @PaymentMethod.Definition
    public String getMethod() {
        return method;
    }

    /**
     * Sets value of method.
     *
     * @param method the method to set.
     */
    public void setMethod(@PaymentMethod.Definition final String method) {
        this.method = method;
    }

    /**
     * Gets value of grouping.
     *
     * @return the grouping.
     */
    public String getGrouping() {
        return grouping;
    }

    /**
     * Sets value of grouping.
     *
     * @param grouping the grouping to set.
     */
    public void setGrouping(final String grouping) {
        this.grouping = grouping;
    }

    /**
     * Gets value of registration.
     *
     * @return the registration.
     */
    @RegistrationType.Definition
    public String getRegistration() {
        return registration;
    }

    /**
     * Sets value of registration.
     *
     * @param registration the registration to set.
     */
    public void setRegistration(@RegistrationType.Definition final String registration) {
        this.registration = registration;
    }

    /**
     * Gets value of recurrence.
     *
     * @return the recurrence.
     */
    @RegistrationType.Definition
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Sets value of recurrence.
     *
     * @param recurrence the recurrence to set.
     */
    public void setRecurrence(@RegistrationType.Definition final String recurrence) {
        this.recurrence = recurrence;
    }

    /**
     * Gets value of redirect.
     *
     * @return the redirect.
     */
    public Boolean getRedirect() {
        return redirect;
    }

    /**
     * Sets value of redirect.
     *
     * @param redirect the redirect to set.
     */
    public void setRedirect(final Boolean redirect) {
        this.redirect = redirect;
    }

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
     * Gets code of button's label what should be used if this network is selected.
     *
     * @return Code of button'S label.
     */
    public String getButton() {
        return button;
    }

    /**
     * Sets code of button's label what should be used if this network is selected.
     *
     * @param button Code of button'S label.
     */
    public void setButton(final String button) {
        this.button = button;
    }

    /**
     * Gets a flag that this network should be pre-selected.
     * <p>
     * Note: only one applicable network or account registration can be selected within a LIST.
     *
     * @return <code>true</code> network should be initially selected.
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Sets a flag that this network should be pre-selected.
     *
     * @param selected <code>true</code> network should be initially selected.
     */
    public void setSelected(final Boolean selected) {
        this.selected = selected;
    }

    /**
     * Gets form data.
     *
     * @return Form data.
     */
    public FormData getFormData() {
        return formData;
    }

    /**
     * Sets form data.
     *
     * @param formData Form data to set.
     */
    public void setFormData(final FormData formData) {
        this.formData = formData;
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
    public List<InputElement> getLocalizedInputElements() {
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
