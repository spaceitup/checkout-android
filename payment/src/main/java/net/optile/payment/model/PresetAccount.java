/*
 * Copyright (c) 2019 optile GmbH
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
    /** Masked account (Simple API, optional) */
    private AccountMask maskedAccount;

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
}
