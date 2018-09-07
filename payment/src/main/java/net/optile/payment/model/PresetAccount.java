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
