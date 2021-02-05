/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.model;

import static com.payoneer.mrs.payment.localization.LocalizationKey.NETWORK_LABEL;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.model.ApplicableNetwork;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.util.PaymentUtils;

/**
 * Class for holding the ApplicableNetwork
 */
public class PaymentNetwork {

    private final ApplicableNetwork network;

    public PaymentNetwork(ApplicableNetwork network) {
        this.network = network;
    }

    public boolean containsLink(String name, URL url) {
        return PaymentUtils.equalsAsString(getLink(name), url);
    }

    public URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    public String getOperationType() {
        return network.getOperationType();
    }

    public ApplicableNetwork getApplicableNetwork() {
        return network;
    }

    public String getPaymentMethod() {
        return network.getMethod();
    }

    public String getCode() {
        return network.getCode();
    }

    public String getLabel() {
        return Localization.translate(network.getCode(), NETWORK_LABEL);
    }

    public String getRecurrence() {
        return network.getRecurrence();
    }

    public String getRegistration() {
        return network.getRegistration();
    }

    public boolean isPreselected() {
        return PaymentUtils.isTrue(network.getSelected());
    }

    public List<InputElement> getInputElements() {
        List<InputElement> elements = network.getInputElements();
        return elements == null ? Collections.emptyList() : elements;
    }

    public String getButton() {
        return network.getButton();
    }

    public boolean compare(PaymentNetwork network) {
        List<InputElement> srcItems = getInputElements();
        List<InputElement> cmpItems = network.getInputElements();

        if (srcItems.size() != cmpItems.size()) {
            return false;
        }
        InputElement srcItem;
        InputElement cmpItem;

        for (int i = 0, e = srcItems.size(); i < e; i++) {
            srcItem = srcItems.get(i);
            cmpItem = cmpItems.get(i);

            if (!(srcItem.getName().equals(cmpItem.getName()) && srcItem.getType().equals(cmpItem.getType()))) {
                return false;
            }
        }
        return true;
    }
}
