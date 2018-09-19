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

import android.content.Intent;
import android.app.Activity;
import android.util.Patterns;
import android.text.TextUtils;

import net.optile.payment.ui.paymentpage.PaymentPageActivity;

/**
 * The PaymentUI is the PaymentPage controller
 */
public final class PaymentUI {
    
    /** The url pointing to the current list */
    private String listUrl;
    
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
     * Get the listUrl in this PaymentUI
     * 
     * @return the listUrl or null if not previously set
     */
    public String getListUrl() {
        return listUrl;
    }

    /** 
     * Show the PaymentPage with the PaymentTheme for the look and feel.
     * 
     * @param activity    the activity that will be notified when this PaymentPage is finished
     * @param requestCode the requestCode to be used for identifying results in the parent activity 
     * @param theme       the optional theme, if null then the default PaymentTheme will be used
     */
    public void showPaymentPage(Activity activity, int requestCode, PaymentTheme theme) {

        if (listUrl == null) {
            throw new IllegalStateException("listUrl must be set before showing the PaymentPage");
        }
        if (activity == null) {
            throw new IllegalArgumentException("activity may not be null");
        }
        if (theme == null) {
            theme = PaymentTheme.createPaymentThemeBuilder().build();
        }
        activity.finishActivity(requestCode);
        Intent intent = PaymentPageActivity.createStartIntent(activity, listUrl, theme);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(0, 0);
    }
}
