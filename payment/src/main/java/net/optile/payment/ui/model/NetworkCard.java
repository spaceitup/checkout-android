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
import net.optile.payment.model.InputElement;

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
    public NetworkCard() {
        this.networks = new ArrayList<>();
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
     * The NetworkCard supports smart selection when it has more than 1 PaymentNetwork.
     * 
     * @return true when this network card supports smart selection, false otherwise. 
     */
    public boolean supportSmartSelection() {
        return networks.size() > 1;
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
     * Get the visible PaymentNetwork, this is either the smart selected or first in the list.
     *
     * @return active PaymentNetwork
     */
    public PaymentNetwork getVisibleNetwork() {
        return smartSelected != null ? smartSelected : networks.get(0);
    }

    /**
     * Get the PaymentNetwork that is currently selected. 
     *
     * @return selected PaymentNetwork, this returns null if there are multiple 
     *         PaymentNetworks and none is smart selected.
     */
    public PaymentNetwork getSelectedNetwork() {
        return networks.size() == 1 ? networks.get(0) : smartSelected;
    }
}
