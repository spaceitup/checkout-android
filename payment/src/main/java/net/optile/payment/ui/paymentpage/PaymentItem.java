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
import net.optile.payment.core.LanguageFile;

/**
 * Base class for payment items like account and network
 */
abstract class PaymentItem {

    private LanguageFile lang;

    private boolean hasExpiryDate;
    
    /** 
     * Ge the operation link, this link can be used to make i.e. a charge request
     * 
     * @return the operation link or null if it does not exist 
     */
    URL getOperationLink() {
        return getLink("operation");
    }

    /** 
     * Get the logo link, this link points to the logo image of this payment item
     * 
     * @return the operation link or null if it does not exist
     */
    URL getLogoLink() {
        return getLink("logo");
    }

    /** 
     * Get the link from the PaymentItem given the name
     * 
     * @return link or null if not found
     */
    abstract URL getLink(String name);

    /**
     * Get the paymentMethod of this PaymentItem 
     * 
     * @return paymentMethod 
     */
    abstract String getPaymentMethod();

    /**
     * Get the language file of this PaymentItem
     * 
     * @return language file  
     */
    LanguageFile getLang() {
        return lang;
    }

    /** 
     * Set the language file in this PaymentItem
     * 
     * @param lang the language file to be set
     */
    void setLang(LanguageFile lang) {
        this.lang = lang;
    }

    boolean hasExpiryDate() {
        return this.hasExpiryDate;
    }

    void setHasExpiryDate(boolean hasExpiryDate) {
        this.hasExpiryDate = hasExpiryDate;
    }
}
