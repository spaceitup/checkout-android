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
import java.util.Properties;

import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.core.LanguageFile;


/**
 * Class for storing the ListResult and the list of supported PaymentMethods
 */
final class PaymentSession {

    final ListResult listResult;

    final List<NetworkGroup> groups;

    final List<AccountItem> accounts;
    
    private int selIndex;

    private LanguageFile lang;

    private String emptyMessage;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param groups list of PaymentGroups supported by this PaymentSession
     */
    PaymentSession(ListResult listResult, List<AccountItem> accounts, List<NetworkGroup> groups) {
        this.listResult = listResult;
        this.accounts = accounts;
        this.groups = groups;
    }

    URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    int getSelIndex() {
        return this.selIndex;
    }

    void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
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
