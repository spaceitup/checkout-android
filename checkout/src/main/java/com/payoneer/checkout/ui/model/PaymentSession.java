/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.validation.Validator;

/**
 * Class for storing the ListResult and the list sections
 */
public final class PaymentSession {
    private final ListResult listResult;
    private final PresetSection presetSection;
    private final AccountSection accountSection;
    private final NetworkSection networkSection;
    private final Validator validator;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param presetSection the optional section with the PresetAccount
     * @param accountSection the optional section with the saved accounts
     * @param networkSection the optional section with payment networks
     * @param validator used to validate input values for this payment session
     */
    public PaymentSession(ListResult listResult, PresetSection presetSection, AccountSection accountSection, NetworkSection networkSection, Validator validator) {
        this.listResult = listResult;
        this.presetSection = presetSection;
        this.accountSection = accountSection;
        this.networkSection = networkSection;
        this.validator = validator;
    }

    public ListResult getListResult() {
        return listResult;
    }

    public boolean containsPresetSection() {
        return presetSection != null;
    }

    public boolean containsAccountSection() {
        return accountSection != null;
    }
    
    public boolean containsNetworkSection() {
        return networkSection != null;
    }
    
    public PresetSection getPresetSection() {
        return presetSection;
    }

    public AccountSection getAccountSection() {
        return accountSection;
    }

    public NetworkSection getNetworkSection() {
        return networkSection;
    }

    public Validator getValidator() {
        return validator;
    }

    public URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    public String getListUrl() {
        URL url = getLink("self");
        return url != null ? url.toString() : null;
    }

    public boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    public boolean isEmpty() {
        return !(containsPresetSection() || containsAccountSection() || containsNetworkSection());
    }

    public boolean containsLink(String name, URL url) {
        if (presetSection != null && presetSection.containsLink(name, url)) {
            return true;
        }
        if (accountSection != null && accountSection.containsLink(name, url)) {
            return true;
        }
        if (networkSection != null && networkSection.containsLink(name, url)) {
            return true;
        }
        return false;
    }
}
