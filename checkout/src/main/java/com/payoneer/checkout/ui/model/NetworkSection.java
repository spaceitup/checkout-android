/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * List Section containing network cards
 *
 */
public final class NetworkSection extends ListSection {

    private final List<NetworkCard> cards;

    /** 
     * Construct a new NetworkSection
     * 
     * @param labelKey localization key
     * @param cards the list of NetworkCards
     */
    public NetworkSection(String labelKey, List<NetworkCard> cards) {
        super(labelKey);
        this.cards = cards;
    }

    /** 
     * Get the NetworkCards in this section
     * 
     * @return list of network cards
     */
    public List<NetworkCard> getNetworkCards() {
        return cards;
    }

    /**
     * Get the number of cards in this section
     *
     * @return number of network cards
     */
    public int getCardSize() {
        return cards != null ? cards.size() : 0;
    }

    /** 
     * Get a list of PaymentNetworks from this network section.
     * 
     * @return list of payment networks inside this section  
     */
    public List<PaymentNetwork> getPaymentNetworks() {
        List<PaymentNetwork> list = new ArrayList<>();
        for (NetworkCard card : cards) {
            list.addAll(card.getPaymentNetworks());
        }
        return list;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsLink(String name, URL url) {
        for (NetworkCard card : cards) {
            if (card.containsLink(name, url)) {
                return true;
            }
        }
        return false;
    }
}
