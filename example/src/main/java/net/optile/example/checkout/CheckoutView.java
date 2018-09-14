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

package net.optile.example.checkout;

/**
 * The interface Checkout view.
 */
public interface CheckoutView {

    /**
     * Is the CheckoutView active
     *
     * @return true when active, false otherwise
     */
    boolean isActive();

    /** 
     * Open the PaymentPage for the provided listUrl
     * 
     * @param listUrl the current listUrl
     */
    void openPaymentPage(String listUrl);
}


