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
import android.util.Log;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for holding the data of a NetworkCard in the list
 */
public final class NetworkCard implements PaymentCard {

    private final List<PaymentNetwork> networks;
    private PaymentNetwork smartSelected;

    /**
     * Construct a new NetworkCard
     *
     * @param networks the list of payment network inside this network card
     * @param elements containing the ordered list of InputElements
     */
    public NetworkCard(List<PaymentNetwork> networks) {
        this.networks = networks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getOperationLink() {
        Log.i("pay_Card", "getOperationLink: " + networks.size());
        return getActiveNetwork().getLink("operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return getActiveNetwork().getPaymentMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return getActiveNetwork().getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageFile getLang() {
        return getActiveNetwork().getLang();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        return getActiveNetwork().getInputElements();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPreselected() {
        return PaymentUtils.isTrue(getActiveNetwork().isPreselected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return getActiveNetwork().getButton();
    }

    /**
     * Get the active PaymentNetwork that is selected in the NetworkCard
     *
     * @return active PaymentNetwork
     */
    public PaymentNetwork getActiveNetwork() {
        return smartSelected != null ? smartSelected : networks.get(0);
    }
}
