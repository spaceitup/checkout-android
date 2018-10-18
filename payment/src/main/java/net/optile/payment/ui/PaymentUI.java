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

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import net.optile.payment.ui.paymentpage.PaymentPageActivity;
import net.optile.payment.validation.Validator;

/**
 * The PaymentUI is the controller to initialize and launch the Payment Page.
 * The Payment Page shows payment methods which can be used to finalize payments through optile's Payment API.
 */
public final class PaymentUI {

    public final static String EXTRA_PAYMENT_RESULT = "paymentresult";

    /** Url pointing to the current list */
    private String listUrl;

    /** Cached payment theme */
    private PaymentTheme theme;

    /** Cached Input value Validator */
    private Validator validator;
    
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
     * Get the listUrl in this PaymentUI
     *
     * @return the listUrl or null if not previously set
     */
    public String getListUrl() {
        return listUrl;
    }

    /**
     * Set the listUrl in this PaymentUI
     *
     * @param listUrl the listUrl to be set in this paymentUI
     */
    public void setListUrl(String listUrl) {

        if (TextUtils.isEmpty(listUrl)) {
            throw new IllegalArgumentException("listUrl may not be null or empty");
        }
        if (!Patterns.WEB_URL.matcher(listUrl).matches()) {
            throw new IllegalArgumentException("listUrl does not have a valid url format");
        }
        this.listUrl = listUrl;
    }

    /** 
     * Set the payment theme
     * 
     * @param theme containing the Payment theme
     */
    public void setPaymentTheme(PaymentTheme theme) {
        this.theme = theme;
    }

    /** 
     * Get the PaymentTheme set in this PaymentUI. This method is not Thread safe and must be called from the Main UI Thread.
     * 
     * @return the set PaymentTheme or the default PaymentTheme 
     */
    public PaymentTheme getPaymentTheme() {

        if (theme == null) {
            theme = PaymentTheme.createPaymentThemeBuilder().build();
        }
        return theme; 
    }

    /** 
     * Set the input value Validator
     * 
     * @param validator input value Validator to be set
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /** 
     * Get the Validator set in this PaymentUI. This method is not Thread safe and must be called from the Main UI Thread.
     * 
     * @return the set Validator or the default Validator 
     */
    public Validator getValidator() {

        if (validator == null) {
            validator = new Validator();
        }
        return validator;
    }
    
    /**
     * Show the PaymentPage with the PaymentTheme for the look and feel.
     *
     * @param activity the activity that will be notified when this PaymentPage is finished
     * @param requestCode the requestCode to be used for identifying results in the parent activity
     */
    public void showPaymentPage(Activity activity, int requestCode) {

        if (listUrl == null) {
            throw new IllegalStateException("listUrl must be set before showing the PaymentPage");
        }
        if (activity == null) {
            throw new IllegalArgumentException("activity may not be null");
        }
        activity.finishActivity(requestCode);
        Intent intent = PaymentPageActivity.createStartIntent(activity, listUrl);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(0, 0);
    }

    private static class InstanceHolder {
        static final PaymentUI INSTANCE = new PaymentUI();
    }
}
