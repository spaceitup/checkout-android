/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.List;

/**
 * Section containing the 
 * 
 *
 */
public final class AccountSection extends ListSection {

    private final List<AccountCard> cards;
    
    /** 
     * Construct a new AccountSection
     * 
     * @param labelKey localization key for this section
     * @param cards the list of AccountCards
     */
    public AccountSection(String labelKey, List<AccountCard> cards) {
        super(labelKey);
        this.cards = cards;
    }

    /** 
     * Get the account cards in this section
     * 
     * @return the account cards stored in this section
     */
    public List<AccountCard> getAccountCards() {
        return cards;
    }

    /**
     * Get the number of cards in this section
     *
     * @return number of account cards
     */
    public int getCardSize() {
        return cards != null ? cards.size() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsLink(String name, URL url) {
        for (AccountCard card : cards) {
            if (card.containsLink(name, url)) {
                return true;
            }
        }
        return false;
    }
}
