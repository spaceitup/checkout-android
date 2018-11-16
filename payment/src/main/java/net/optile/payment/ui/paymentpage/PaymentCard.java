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
import java.util.List;
import net.optile.payment.model.InputElement;
import net.optile.payment.core.LanguageFile;

/**
 * Interface for payment cards like AccountCard and NetworkCard
 */
interface PaymentCard {

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
     * Get the language file of this PaymentCard
     * 
     * @return language file  
     */
    LanguageFile getLang();

    /**
     * Does this PaymentCard has the ExpiryDate input elements 
     * 
     * @return true when having the expiry date, false otherwise 
     */
    boolean hasExpiryDate();

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
