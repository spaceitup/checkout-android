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
 * The PaymentUI for creating the PaymentPage
 */
public final class PaymentUI {

    private static class InstanceHolder {
        static final PaymentUI INSTANCE = new PaymentUI();
    }
    
    private PaymentUI() {
    }

    /** 
     * Get the instance of this PaymentUI
     * 
     * @return the instance of this PaymentUI 
     */
    public static PaymentUI getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /** 
     * Create a new PaymentPageIntentBuilder, this intent can be used to launch the PaymentPage.
     * 
     * @return a new instance of the PaymentPageIntentBuilder
     */
    public PaymentPageIntentBuilder createPaymentPageIntentBuilder() {
        return new PaymentPageIntentBuilder();
    }

    /**
     * The Builder for constructing a PaymentPage Intent.
     * This intent can then be used to launch the PaymentPage.
     */
    public class PaymentPageIntentBuilder {
    }
}
