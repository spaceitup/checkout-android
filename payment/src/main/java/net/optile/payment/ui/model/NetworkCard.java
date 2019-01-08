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
import java.util.ArrayList;
import java.util.List;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.PaymentMethod;

/**
 * Class for holding the data of a NetworkCard in the list
 */
public final class NetworkCard implements PaymentCard {

    private final List<PaymentNetwork> networks;
    private final List<PaymentNetwork> smartSelected;
    private final List<PaymentNetwork> smartBuffer;

    /**
     * Construct a new NetworkCard
     */
    public NetworkCard() {
        this.networks = new ArrayList<>();
        this.smartSelected = new ArrayList<>();
        this.smartBuffer = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getOperationLink() {
        return getVisibleNetwork().getLink("operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return getVisibleNetwork().getPaymentMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        PaymentNetwork network = getSelectedNetwork();
        return network != null ? network.getCode() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageFile getLang() {
        return getVisibleNetwork().getLang();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        return getVisibleNetwork().getInputElements();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputElement getInputElement(String name) {

        for (InputElement element : getInputElements()) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPreselected() {

        for (PaymentNetwork network : networks) {
            if (network.isPreselected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return getVisibleNetwork().getButton();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTextInputChanged(String type, String text) {
        if (networks.size() == 1) {
            return false;
        }
        switch (getPaymentMethod()) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                if (PaymentInputType.ACCOUNT_NUMBER.equals(type)) {
                    return validateSmartSelected(text);
                }
        }
        return false;
    }

    /**
     * Add a PaymentNetwork to this NetworkCard, adding may fail if InputElements of this PaymentNetwork are not similar with InputElements
     * of previously added PaymentNetworks.
     *
     * @param network to be added to this NetworkCard
     * @return true when this network was added successfully or false otherwise
     */
    public boolean addPaymentNetwork(PaymentNetwork network) {

        if (networks.size() > 0 && !network.compare(networks.get(0))) {
            return false;
        }
        networks.add(network);
        return true;
    }

    /**
     * Get the list of PaymentNetworks supported by this NetworkCard.
     *
     * @return the list of PaymentNetworks.
     */
    public List<PaymentNetwork> getPaymentNetworks() {
        return networks;
    }

    /**
     * Get the list of smart selected PaymentNetworks.
     *
     * @return the list of smart selected PaymentNetworks.
     */
    public List<PaymentNetwork> getSmartSelected() {
        return smartSelected;
    }

    /**
     * Get the visible PaymentNetwork, this is either the first in the list of smart selected networks or the first network if
     * none are smart selected.
     *
     * @return active PaymentNetwork
     */
    public PaymentNetwork getVisibleNetwork() {

        if (smartSelected.size() != 0) {
            return smartSelected.get(0);
        }
        return networks.get(0);
    }

    /**
     * The NetworkCard supports smart selection when it has more than 1 PaymentNetwork.
     *
     * @return true when this network card supports smart selection, false otherwise.
     */
    public boolean supportSmartSelection() {
        return networks.size() > 1;
    }

    /**
     * Check if the PaymentNetwork is smart selected, it is smart selected when the provided number input matches
     * the regex of this PaymentMethod in the groups settings file. A PaymentMethod is always smart selected when
     * there is only one PaymentMethod in the NetworkCard.
     *
     * @param network to check if smart selection
     * @return true when smart selected, false otherwise
     */
    public boolean isSmartSelected(PaymentNetwork network) {
        if (networks.size() == 1 && networks.get(0) == network) {
            return true;
        }
        return smartSelected.contains(network);
    }

    /**
     * Get the PaymentNetwork that is currently selected. This method may return null when there are multiple smart selected PaymentNetworks.
     *
     * @return selected PaymentNetwork, may return null.
     */
    public PaymentNetwork getSelectedNetwork() {

        if (networks.size() == 1) {
            return networks.get(0);
        }
        return smartSelected.size() == 1 ? smartSelected.get(0) : null;
    }

    private boolean validateSmartSelected(String text) {
        smartBuffer.clear();

        for (PaymentNetwork network : networks) {
            if (network.validateSmartSelected(text)) {
                smartBuffer.add(network);
            }
        }
        if (!smartSelected.equals(smartBuffer)) {
            smartSelected.clear();
            smartSelected.addAll(smartBuffer);
            return true;
        }
        return false;
    }
}
