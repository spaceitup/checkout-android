/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.net.URL;
import java.util.Map;

/**
 * An information about preset account.
 */
public class PresetAccount {
    /** Links (Simple API, always present) */
    private Map<String, URL> links;
    /** Network code (Simple API, always present) */
    private String code;
    /** Simple API, always present */
    @OperationType.Definition
    private String operationType;
    /** Masked account (Simple API, optional) */
    private AccountMask maskedAccount;
    /** PCI API, optional */
    private Redirect redirect;
    /** Simple API, always present */
    @PaymentMethod.Definition
    private String method;

    /**
     * Gets links.
     *
     * @return Links map.
     */
    public Map<String, URL> getLinks() {
        return links;
    }

    /**
     * Sets links.
     *
     * @param links Map of the links.
     */
    public void setLinks(final Map<String, URL> links) {
        this.links = links;
    }

    /**
     * Gets network code.
     *
     * @return Network code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets network code.
     *
     * @param code Network code.
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Gets value of operationType
     *
     * @return the operationType.
     */
    @OperationType.Definition
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets value of operationType
     *
     * @param operationType the operation type to set.
     */
    public void setOperationType(@OperationType.Definition final String operationType) {
        this.operationType = operationType;
    }

    /**
     * Gets value of redirect.
     *
     * @return the redirect.
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * Sets value of redirect.
     *
     * @param redirect the redirect to set.
     */
    public void setRedirect(final Redirect redirect) {
        this.redirect = redirect;
    }

    /**
     * Gets masked account.
     *
     * @return Masked account.
     */
    public AccountMask getMaskedAccount() {
        return maskedAccount;
    }

    /**
     * Sets masked account.
     *
     * @param maskedAccount Masked account.
     */
    public void setMaskedAccount(final AccountMask maskedAccount) {
        this.maskedAccount = maskedAccount;
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
}
