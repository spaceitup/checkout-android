/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.validation.Validator;

/**
 * Class for storing the ListResult and the list of supported PaymentMethods
 */
public final class PaymentSession {
    private final ListResult listResult;
    private final PresetCard presetCard;
    private final List<AccountCard> accounts;
    private final List<NetworkCard> networks;
    private final Validator validator;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param presetCard the optional PresetCard with the PresetAccount
     * @param accounts list of AccountCards supported by this PaymentSession
     * @param networks list of NetworkCards supported by this PaymentSession
     * @param validator used to validate input values for this payment session
     */
    public PaymentSession(ListResult listResult, PresetCard presetCard, List<AccountCard> accounts, List<NetworkCard> networks,
        Validator validator) {
        this.listResult = listResult;
        this.presetCard = presetCard;
        this.accounts = accounts;
        this.networks = networks;
        this.validator = validator;
    }

    public ListResult getListResult() {
        return listResult;
    }

    public PresetCard getPresetCard() {
        return presetCard;
    }

    public List<AccountCard> getAccountCards() {
        return accounts;
    }

    public List<NetworkCard> getNetworkCards() {
        return networks;
    }

    public List<PaymentNetwork> getPaymentNetworks() {
        List<PaymentNetwork> list = new ArrayList<>();
        for (NetworkCard card : networks) {
            list.addAll(card.getPaymentNetworks());
        }
        return list;
    }

    public String lookupPaymentMethod(String code) {
        for (NetworkCard card : networks) {
            String method = card.lookupPaymentMethod(code);
            if (method != null) {
                return method;
            }
        }
        for (AccountCard card : accounts) {
            String method = card.lookupPaymentMethod(code);
            if (method != null) {
                return method;
            }
        }
        return null;
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

    public String getOperationType() {
        return listResult.getOperationType();
    }

    public boolean hasPresetCard() {
        return presetCard != null;
    }

    public int getNetworkCardSize() {
        return networks != null ? networks.size() : 0;
    }

    public int getAccountCardSize() {
        return accounts != null ? accounts.size() : 0;
    }

    public int getApplicableNetworkSize() {
        Networks nw = listResult.getNetworks();
        if (nw == null) {
            return 0;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        return an != null ? an.size() : 0;
    }

    public boolean containsLink(String name, URL url) {
        if (presetCard != null && presetCard.containsLink(name, url)) {
            return true;
        }
        for (AccountCard card : accounts) {
            if (card.containsLink(name, url)) {
                return true;
            }
        }
        for (NetworkCard card : networks) {
            if (card.containsLink(name, url)) {
                return true;
            }
        }
        return false;
    }
}
