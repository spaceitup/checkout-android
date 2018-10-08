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
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.model.Interaction;

/**
 * Class for storing the ListResult and the list of supported PaymentMethods
 */
final class PaymentSession {

    final ListResult listResult;

    final List<PaymentGroup> groups;

    final int selIndex;

    private Properties language;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param groups list of PaymentGroups supported by this PaymentSession
     * @param selIndex the index of the preselected group
     */
    PaymentSession(ListResult listResult, List<PaymentGroup> groups, int selIndex) {
        this.listResult = listResult;
        this.groups = groups;
        this.selIndex = selIndex;
    }

    URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    void setLanguage(Properties language) {
        this.language = language;
    }

    String translate(String key, String defValue) {
        return language != null ? language.getProperty(key, defValue) : defValue;
    }

    String translateInteraction(Interaction interaction) {
        StringBuilder sb = new StringBuilder("interaction.");
        sb.append(interaction.getCode()).append(".").append(interaction.getReason());
        return translate(sb.toString(), sb.toString());
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
