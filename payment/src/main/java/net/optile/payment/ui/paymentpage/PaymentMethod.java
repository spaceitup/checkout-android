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
import java.util.Map;
import java.util.Properties;
import net.optile.payment.model.ApplicableNetwork;

/**
 * Class for holding the ApplicableNetwork with its localized language file
 */
final class PaymentMethod {

    final ApplicableNetwork network;

    private Properties language;

    /** 
     * Construct a new PaymentMethod object
     * 
     * @param network ApplicableNetwork used in this PaymentMethod 
     */
    PaymentMethod(ApplicableNetwork network) {
        this.network = network;
    }

    URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    void setLanguage(Properties language) {
        this.language = language;
    }

    String getCode() {
        return network.getCode();
    }
    
    String getLabel() {
        return network.getLabel();
    }

    String translate(String key, String defValue) {
        return language != null ? language.getProperty(key, defValue) : defValue;
    }
}
