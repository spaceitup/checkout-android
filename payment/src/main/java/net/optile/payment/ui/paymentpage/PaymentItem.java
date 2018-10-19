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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for holding the ApplicableNetwork with its localized language file
 */
final class PaymentItem {

    final ApplicableNetwork network;

    private Properties language;

    /**
     * Construct a new PaymentItem object
     *
     * @param network ApplicableNetwork used in this PaymentItem
     */
    PaymentItem(ApplicableNetwork network) {
        this.network = network;
    }

    URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    String getCode() {
        return network.getCode();
    }

    String getLabel() {
        return network.getLabel();
    }

    boolean isSelected() {
        return PaymentUtils.isTrue(network.getSelected());
    }

    List<InputElement> getInputElements() {
        List<InputElement> elements = network.getLocalizedInputElements();
        return elements == null ? new ArrayList<>() : elements;
    }

    void setLanguage(Properties language) {
        this.language = language;
    }

    String translate(String key, String defValue) {
        return language != null && key != null ? language.getProperty(key, defValue) : defValue;
    }

    String translateError(String error) {
        StringBuilder sb = new StringBuilder("error.").append(error);
        return translate(sb.toString(), null);
    }

    String getButton() {
        return network.getButton();
    }

    String getPaymentMethod() {
        return network.getMethod();
    }
}
