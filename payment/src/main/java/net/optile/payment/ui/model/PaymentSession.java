/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.model;

import java.net.URL;
import java.util.List;
import java.util.Map;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;

/**
 * Class for storing the ListResult and the list of supported PaymentMethods
 */
public final class PaymentSession {

    public final ListResult listResult;
    public final PresetCard presetCard;
    public final List<AccountCard> accounts;
    public final List<NetworkCard> networks;
    private LanguageFile lang;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param presetCard the optional PresetCard with the PresetAccount
     * @param accounts list of AccountCards supported by this PaymentSession
     * @param networks list of NetworkCards supported by this PaymentSession
     */
    public PaymentSession(ListResult listResult, PresetCard presetCard, List<AccountCard> accounts, List<NetworkCard> networks) {
        this.listResult = listResult;
        this.presetCard = presetCard;
        this.accounts = accounts;
        this.networks = networks;
    }

    public URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    public boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    public LanguageFile getLang() {
        return lang;
    }

    public void setLang(LanguageFile lang) {
        this.lang = lang;
    }

    public String getOperationType() {
        return listResult.getOperationType();
    }

    public int getApplicableNetworkSize() {
        Networks nw = listResult.getNetworks();
        if (nw == null) {
            return 0;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        return an != null ? an.size() : 0;
    }
}
