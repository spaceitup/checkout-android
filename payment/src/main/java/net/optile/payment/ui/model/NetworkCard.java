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

package net.optile.payment.ui.model;

import java.net.URL;
import java.util.List;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for holding the data of a NetworkCard in the list
 */
public final class NetworkCard implements PaymentCard {

    public final PaymentNetwork network;
    public final List<InputElement> elements;

    /**
     * Construct a new NetworkCard
     *
     * @param network the network payment shown in this NetworkCard
     * @param elements containing the ordered list of InputElements
     */
    public NetworkCard(PaymentNetwork network, List<InputElement> elements) {
        this.network = network;
        this.elements = elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getOperationLink() {
        return network.getLink("operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return network.getPaymentMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return network.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageFile getLang() {
        return network.getLang();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        return elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPreselected() {
        return PaymentUtils.isTrue(network.isPreselected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return network.getButton();
    }

    /**
     * Get the active PaymentNetwork that is selected in the NetworkCard
     *
     * @return active PaymentNetwork
     */
    public PaymentNetwork getActivePaymentNetwork() {
        return network;
    }
}
