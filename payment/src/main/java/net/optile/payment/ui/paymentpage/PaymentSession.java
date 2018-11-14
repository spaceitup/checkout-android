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

package net.optile.payment.ui.paymentpage;

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
final class PaymentSession {

    final ListResult listResult;

    final List<AccountCard> accounts;

    final List<NetworkCard> networks;
    
    private LanguageFile lang;

    private String emptyMessage;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param accounts list of AccountCards supported by this PaymentSession
     * @param networks list of NetworkCards supported by this PaymentSession
     */
    PaymentSession(ListResult listResult, List<AccountCard> accounts, List<NetworkCard> networks) {
        this.listResult = listResult;
        this.accounts = accounts;
        this.networks = networks;
    }

    URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    void setLang(LanguageFile lang) {
        this.lang = lang;
    }

    LanguageFile getLang() {
        return lang;
    }
    
    String getEmptyMessage() {
        return emptyMessage;
    }

    void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    int getApplicableNetworkSize() {
        Networks nw = listResult.getNetworks();
        if (nw == null) {
            return 0;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        return an != null ? an.size() : 0;
    }
}
