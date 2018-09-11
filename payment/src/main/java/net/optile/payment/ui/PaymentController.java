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

package net.optile.payment.ui;

/**
 * The PaymentController
 */
public final class PaymentController {

    private static class InstanceHolder {
        static final PaymentController INSTANCE = new PaymentController();
    }
    
    private PaymentController() {
    }

    /** The url of the current list */
    private String listUrl;
    
    /** 
     * Get the instance of this PaymentController
     * 
     * @return the instance of this PaymentController 
     */
    public static PaymentController getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /** 
     * Set the listUrl in this PaymentController
     * 
     * @param listUrl the listUrl to be set in this controller
     */
    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    /** 
     * Get the listUrl in this PaymentController
     * 
     * @return the listUrl in this PaymentController
     */
    public String getListUrl() {
        return listUrl;
    }
}
