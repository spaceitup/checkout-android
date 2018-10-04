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

import java.util.List;
import java.util.Properties;

import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;

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

    void setLanguage(Properties language) {
        this.language = language;
    }

    String translate(String key, String defValue) {
        return language != null ? language.getProperty(key, defValue) : defValue;
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
