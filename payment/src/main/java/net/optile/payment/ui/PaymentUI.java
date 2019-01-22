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
import android.content.pm.ActivityInfo;

import net.optile.payment.R;
import net.optile.payment.ui.page.PaymentPageActivity;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * The PaymentUI is the controller to initialize and launch the Payment Page.
 * The Payment Page shows payment methods which can be used to finalize payments through optile's Payment API.
 */
public final class PaymentUI {

    public final static int RESULT_CODE_OK = Activity.RESULT_FIRST_USER;
    public final static int RESULT_CODE_CANCELED = Activity.RESULT_FIRST_USER + 1;
    public final static int RESULT_CODE_ERROR = Activity.RESULT_FIRST_USER + 2;
    public final static String EXTRA_PAYMENT_RESULT = "paymentresult";

    /** The orientation of the Payment page, by default it is in locked mode */
    private int orientation;
    
    /** The url pointing to the current list */
    private String listUrl;

    /** The cached payment theme */
    private PaymentTheme theme;

    /** The validation resource file id */
    private int validationResId;

    /** The group resource file id */
    private int groupResId;

    private PaymentUI() {
        this.orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED;        
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
     * Get the orientation mode for the PaymentPage, by default the ActivityInfo.SCREEN_ORIENTATION_LOCKED is used. 
     * 
     * @return orientation mode
     */
    public int getOrientation() {
        return this.orientation;
    }

    /** 
     * Set the orientation of the Payment Page, the following orientation modes are supported:
     * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT

     *
     * @param orientation mode for the Payment Page 
     */
    public void setOrientation(int orientation) {

        switch (orientation) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:            
            case ActivityInfo.SCREEN_ORIENTATION_LOCKED:
                this.orientation = orientation;
                break;
            default:
                throw new IllegalArgumentException("Orientation mode is not supported: " + orientation);
        }
    }
    
    /**
     * Get the PaymentTheme set in this PaymentUI. This method is not Thread safe and must be called from the Main UI Thread.
     *
     * @return the set PaymentTheme or the default PaymentTheme
     */
    public PaymentTheme getPaymentTheme() {
        return theme;
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
     * Get the validation resource file id. The validation file contains the validation settings for credit and debit cards.
     *
     * @return the validation resource id
     */
    public int getValidationResId() {
        return validationResId;
    }

    /**
     * Set the validation resource file id. The validation file contains the validation settings for credit and debit cards.
     *
     * @param validationResId containing the resource id of the validation file.
     */
    public void setValidationResId(int validationResId) {
        this.validationResId = validationResId;
    }

    /**
     * Get the group resource file id. The group file defines how payment methods are grouped in the payment page.
     *
     * @return the group resource id
     */
    public int getGroupResId() {
        return groupResId;
    }

    /**
     * Set the group resource file id. The group file defines how payment methods are grouped in the payment page.
     *
     * @param groupResId contains the resource id of the group file.
     */
    public void setGroupResId(int groupResId) {
        this.groupResId = groupResId;
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
        if (theme == null) {
            setPaymentTheme(PaymentTheme.createDefault());
        }
        if (validationResId == 0) {
            setValidationResId(R.raw.validations);
        }
        if (groupResId == 0) {
            setGroupResId(R.raw.groups);
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
