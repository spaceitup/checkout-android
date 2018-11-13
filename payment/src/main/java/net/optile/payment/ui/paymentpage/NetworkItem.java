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
import net.optile.payment.core.LanguageFile;

/**
 * Class for holding the ApplicableNetwork with its localized language file
 */
class NetworkItem extends PaymentItem {

    private final ApplicableNetwork network;
    
    /**
     * Construct a new NetworkItem object
     *
     * @param network ApplicableNetwork used in this NetworkItem
     */
    NetworkItem(ApplicableNetwork network) {
        this.network = network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getLink(String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    boolean isSelected() {
        return PaymentUtils.isTrue(network.getSelected());
    }

    List<InputElement> getInputElements() {
        List<InputElement> elements = network.getLocalizedInputElements();
        return elements == null ? new ArrayList<>() : elements;
    }

    String getButton() {
        return network.getButton();
    }

}
