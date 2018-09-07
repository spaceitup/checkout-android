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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.support.annotation.StringDef;

/**
 * This class is designed to hold information about applicable payment network.
 */
public class ApplicableNetwork {

    /**
     * The constant METHOD_BANK_TRANSFER.
     */
    public final static String METHOD_BANK_TRANSFER = "BANK_TRANSFER";
    /**
     * The constant METHOD_BILLING_PROVIDER.
     */
    public final static String METHOD_BILLING_PROVIDER = "BILLING_PROVIDER";
    /**
     * The constant METHOD_CASH_ON_DELIVERY.
     */
    public final static String METHOD_CASH_ON_DELIVERY = "CASH_ON_DELIVERY";
    /**
     * The constant METHOD_CHECK_PAYMENT.
     */
    public final static String METHOD_CHECK_PAYMENT = "CHECK_PAYMENT";
    /**
     * The constant METHOD_CREDIT_CARD.
     */
    public final static String METHOD_CREDIT_CARD = "CREDIT_CARD";
    /**
     * The constant METHOD_DEBIT_CARD.
     */
    public final static String METHOD_DEBIT_CARD = "DEBIT_CARD";
    /**
     * The constant METHOD_DIRECT_DEBIT.
     */
    public final static String METHOD_DIRECT_DEBIT = "DIRECT_DEBIT";
    /**
     * The constant METHOD_ELECTRONIC_INVOICE.
     */
    public final static String METHOD_ELECTRONIC_INVOICE = "ELECTRONIC_INVOICE";
    /**
     * The constant METHOD_GIFT_CARD.
     */
    public final static String METHOD_GIFT_CARD = "GIFT_CARD";
    /**
     * The constant METHOD_MOBILE_PAYMENT.
     */
    public final static String METHOD_MOBILE_PAYMENT = "MOBILE_PAYMENT";
    /**
     * The constant METHOD_ONLINE_BANK_TRANSFER.
     */
    public final static String METHOD_ONLINE_BANK_TRANSFER = "ONLINE_BANK_TRANSFER";
    /**
     * The constant METHOD_OPEN_INVOICE.
     */
    public final static String METHOD_OPEN_INVOICE = "OPEN_INVOICE";
    /**
     * The constant METHOD_PREPAID_CARD.
     */
    public final static String METHOD_PREPAID_CARD = "PREPAID_CARD";
    /**
     * The constant METHOD_TERMINAL.
     */
    public final static String METHOD_TERMINAL = "TERMINAL";
    /**
     * The constant METHOD_WALLET.
     */
    public final static String METHOD_WALLET = "WALLET";
    /**
     * The constant METHOD_UNKNOWN.
     */
    public final static String METHOD_UNKNOWN = "UnknownMethod";
    /**
     * The constant REGTYPE_NONE.
     */
    public final static String REGTYPE_NONE = "NONE";
    /**
     * The constant REGTYPE_OPTIONAL.
     */
    public final static String REGTYPE_OPTIONAL = "OPTIONAL";
    /**
     * The constant REGTYPE_FORCED.
     */
    public final static String REGTYPE_FORCED = "FORCED";
    /**
     * The constant REGTYPE_OPTIONAL_PRESELECTED.
     */
    public final static String REGTYPE_OPTIONAL_PRESELECTED = "OPTIONAL_PRESELECTED";
    /**
     * The constant REGTYPE_FORCED_DISPLAYED.
     */
    public final static String REGTYPE_FORCED_DISPLAYED = "FORCED_DISPLAYED";
    /**
     * The constant REGTYPE_UNKNOWN.
     */
    public final static String REGTYPE_UNKNOWN = "REGTYPE_UNKNOWN";
    /** Simple API, always present */
    private String code;
    /** Simple API, always present */
    private String label;
    /** Simple API, always present */
    @PaymentMethod
    private String method;
    /** Simple API, always present */
    private String grouping;
    /** Simple API, always present */
    @RegistrationType
    private String registration;
    /** Simple API, always present */
    @RegistrationType
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
    @PaymentMethod
    public String getMethod() {
        return method;
    }

    /**
     * Sets value of method.
     *
     * @param method the method to set.
     */
    public void setMethod(@PaymentMethod final String method) {
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
    @RegistrationType
    public String getRegistration() {
        return registration;
    }

    /**
     * Sets value of registration.
     *
     * @param registration the registration to set.
     */
    public void setRegistration(@RegistrationType final String registration) {
        this.registration = registration;
    }

    /**
     * Gets value of recurrence.
     *
     * @return the recurrence.
     */
    @RegistrationType
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Sets value of recurrence.
     *
     * @param recurrence the recurrence to set.
     */
    public void setRecurrence(@RegistrationType final String recurrence) {
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

    /**
     * Gets method as a checked value.
     * If the value does not match any predefined modes then return
     * METHOD_UNKNOWN.
     *
     * @return the method.
     */
    @PaymentMethod
    public String getCheckedMethod() {

        if (this.method != null) {
            switch (this.method) {
                case METHOD_BANK_TRANSFER:
                case METHOD_BILLING_PROVIDER:
                case METHOD_CASH_ON_DELIVERY:
                case METHOD_CHECK_PAYMENT:
                case METHOD_CREDIT_CARD:
                case METHOD_DEBIT_CARD:
                case METHOD_DIRECT_DEBIT:
                case METHOD_ELECTRONIC_INVOICE:
                case METHOD_GIFT_CARD:
                case METHOD_MOBILE_PAYMENT:
                case METHOD_ONLINE_BANK_TRANSFER:
                case METHOD_OPEN_INVOICE:
                case METHOD_PREPAID_CARD:
                case METHOD_TERMINAL:
                case METHOD_WALLET:
                    return this.method;
            }
        }
        return METHOD_UNKNOWN;
    }

    /**
     * Gets registration as a checked value.
     * If the value does not match any predefined modes then return
     * REGTYPE_UNKNOWN.
     *
     * @return the registration.
     */
    @RegistrationType
    public String getCheckedRegistration() {

        if (this.registration != null) {
            switch (this.registration) {
                case REGTYPE_NONE:
                case REGTYPE_OPTIONAL:
                case REGTYPE_FORCED:
                case REGTYPE_OPTIONAL_PRESELECTED:
                case REGTYPE_FORCED_DISPLAYED:
                    return this.registration;
            }
        }
        return REGTYPE_UNKNOWN;
    }

    /**
     * Gets recurrence as a checked value.
     * If the value does not match any predefined modes then return
     * REGTYPE_UNKNOWN.
     *
     * @return the recurrence.
     */
    @RegistrationType
    public String getCheckedRecurrence() {

        if (this.recurrence != null) {
            switch (this.recurrence) {
                case REGTYPE_NONE:
                case REGTYPE_OPTIONAL:
                case REGTYPE_FORCED:
                case REGTYPE_OPTIONAL_PRESELECTED:
                case REGTYPE_FORCED_DISPLAYED:
                    return this.recurrence;
            }
        }
        return REGTYPE_UNKNOWN;
    }

    /**
     * The interface Payment method.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ METHOD_BANK_TRANSFER,
        METHOD_BILLING_PROVIDER,
        METHOD_CASH_ON_DELIVERY,
        METHOD_CHECK_PAYMENT,
        METHOD_CREDIT_CARD,
        METHOD_DEBIT_CARD,
        METHOD_DIRECT_DEBIT,
        METHOD_ELECTRONIC_INVOICE,
        METHOD_GIFT_CARD,
        METHOD_MOBILE_PAYMENT,
        METHOD_ONLINE_BANK_TRANSFER,
        METHOD_OPEN_INVOICE,
        METHOD_PREPAID_CARD,
        METHOD_TERMINAL,
        METHOD_WALLET,
        METHOD_UNKNOWN })
    public @interface PaymentMethod {
    }

    /**
     * The interface Registration type.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ REGTYPE_NONE,
        REGTYPE_OPTIONAL,
        REGTYPE_FORCED,
        REGTYPE_OPTIONAL_PRESELECTED,
        REGTYPE_FORCED_DISPLAYED,
        REGTYPE_UNKNOWN })
    public @interface RegistrationType {
    }
}
