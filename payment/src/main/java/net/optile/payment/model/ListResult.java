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
 * This class is designed to hold list of payment networks available for particular transaction based on provided information and result of
 * initialized payment session.
 * <p>
 * An instance of this object is returned as a result of new <code>Transaction</code> initialization, or during list status update via GET
 * method.
 */
public class ListResult {
    /** Simple API, always present */
    private Map<String, URL> links;
    /** Simple API, always present */
    private String resultInfo;
    /** Simple API, optional, always present in response to action (POST, UPDATE) */
    private Interaction interaction;
    /** Simple API, optional */
    private List<AccountRegistration> accounts;
    /** Simple API, optional, always present in native LIST */
    private Networks networks;
    /** Advanced API, optional */
    private ExtraElements extraElements;
    /** Preset account, Simple API, optional, could present only in the LIST-for-PRESET */
    private PresetAccount presetAccount;
    /** LIST type based on operation of next referred actions, could be one of CHARGE, PRESET, PAYOUT, UPDATE. */
    private String operationType;
    /** Indicates whether this LIST is explicitly initialized with permission or denial to delete accounts. */
    private Boolean allowDelete;

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
    public void setLinks(Map<String, URL> links) {
        this.links = links;
    }

    /**
     * Gets value of resultInfo.
     *
     * @return the resultInfo.
     */
    public String getResultInfo() {
        return resultInfo;
    }

    /**
     * Sets value of resultInfo.
     *
     * @param resultInfo the resultInfo to set.
     */
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    /**
     * Gets value of interaction.
     *
     * @return the interaction.
     */
    public Interaction getInteraction() {
        return interaction;
    }

    /**
     * Sets value of interaction.
     *
     * @param interaction the interaction to set.
     */
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    /**
     * Gets value of accounts.
     *
     * @return the accounts.
     */
    public List<AccountRegistration> getAccounts() {
        return accounts;
    }

    /**
     * Sets value of accounts.
     *
     * @param accounts the accounts to set.
     */
    public void setAccounts(List<AccountRegistration> accounts) {
        this.accounts = accounts;
    }

    /**
     * Gets value of networks.
     *
     * @return the networks.
     */
    public Networks getNetworks() {
        return networks;
    }

    /**
     * Sets value of networks.
     *
     * @param networks the networks to set.
     */
    public void setNetworks(Networks networks) {
        this.networks = networks;
    }

    /**
     * Gets value of extraElements.
     *
     * @return the extraElements.
     */
    public ExtraElements getExtraElements() {
        return extraElements;
    }

    /**
     * Sets value of extraElements.
     *
     * @param extraElements the extraElements to set.
     */
    public void setExtraElements(ExtraElements extraElements) {
        this.extraElements = extraElements;
    }

    /**
     * Gets preset account.
     *
     * @return Preset account object.
     */
    public PresetAccount getPresetAccount() {
        return presetAccount;
    }

    /**
     * Sets preset account.
     *
     * @param presetAccount Preset account object.
     */
    public void setPresetAccount(final PresetAccount presetAccount) {
        this.presetAccount = presetAccount;
    }

    /**
     * Gets the LIST type based on operation of next referred action. Could be one of <code>CHARGE</code>, <code>PRESET</code>,
     * <code>PAYOUT</code>, <code>UPDATE</code>. Using this information the client could determine the type of the list like LIST-for-UPDATE
     * or LIST-for-PAYOUT and so on.
     *
     * @return Operation of referred actions.
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets LIST operation type.
     *
     * @param operationType Operation type value.
     */
    public void setOperationType(final String operationType) {
        this.operationType = operationType;
    }

    /**
     * Indicates whether this LIST is explicitly initialized with permission or denial to delete accounts.
     * <p>
     * If set to <code>true</code> the deletion of registered account is permitted by the merchant during this LIST session. If set to
     * <code>false</code> the deletion is disallowed.
     * <p>
     * If nothing is set the default behavior applies: deletion is only allowed for LISTs in the <code>updateOnly</code> mode.
     *
     * @return the value of allowDelete flag.
     */
    public Boolean getAllowDelete() {
        return allowDelete;
    }

    /**
     * Sets value of allowDelete flag.
     *
     * @param allowDelete the <code>true</code> to allow deleting of accounts, <code>false</code> to disallow that.
     */
    public void setAllowDelete(final Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }
}
