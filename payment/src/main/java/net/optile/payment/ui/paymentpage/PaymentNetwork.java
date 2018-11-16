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

import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.core.LanguageFile;

/**
 * Class for holding the ApplicableNetwork with its localized language file
 */
class PaymentNetwork {

    private final ApplicableNetwork network;
    private LanguageFile lang;
    
    PaymentNetwork(ApplicableNetwork network) {
        this.network = network;
    }

    URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    String getPaymentMethod() {
        return network.getMethod();
    }
    
    String getCode() {
        return network.getCode();
    }

    String getLabel() {
        return network.getLabel();
    }

    String getRecurrence() {
        return network.getRecurrence();
    }

    String getRegistration() {
        return network.getRegistration();
    }

    boolean isPreselected() {
        return PaymentUtils.isTrue(network.getSelected());
    }

    List<InputElement> getInputElements() {
        List<InputElement> elements = network.getLocalizedInputElements();
        return elements == null ? new ArrayList<>() : elements;
    }

    String getButton() {
        return network.getButton();
    }

    LanguageFile getLang() {
        return lang;
    }

    void setLang(LanguageFile lang) {
        this.lang = lang;
    }
}
