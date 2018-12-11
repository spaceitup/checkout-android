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

/**
 * Interface for payment cards like AccountCard and NetworkCard
 */
public interface PaymentCard {

    /**
     * Get the operation link, this link can be used to make i.e. a charge request
     *
     * @return the operation link or null if it does not exist
     */
    URL getOperationLink();

    /**
     * Get the paymentMethod of this PaymentCard
     *
     * @return paymentMethod
     */
    String getPaymentMethod();

    /**
     * Get the code of this PaymentCard
     *
     * @return code
     */
    String getCode();
    
    /**
     * Get the language file of this PaymentCard
     *
     * @return language file
     */
    LanguageFile getLang();

    /**
     * Is this card preselected by the Payment API
     *
     * @return true when preselected, false otherwise
     */
    boolean isPreselected();

    /**
     * Get the action button label
     *
     * @return the action button label or null if not found
     */
    String getButton();

    /**
     * Get the list of input elements supported by this  payment card
     *
     * @return list of InputElements
     */
    List<InputElement> getInputElements();
}
