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

import java.util.ArrayList;
import java.util.List;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "payment_PaymentPagePresenter";

    /**
     * The PaymentPageView that implements the view part of the MVP
     */
    private PaymentPageView view;

    /**
     * The list of currently loaded payment methods
     */
    private List<PaymentListItem> items;

    /**
     * Is this presenter started
     */
    private boolean started;

    /** The list item type */
    private int listItemType;

    /** The listUrl pointing to the ListResult in the Payment API */
    private String listUrl;
    
    /**
     * Create a new PaymentPagePresenter
     */
    PaymentPagePresenter(PaymentPageView view) {
        this.view = view;
        this.items = new ArrayList<PaymentListItem>();
    }

    /** 
     * Notify this presenter that it should be stopped
     */
    void onStop() {
        this.started = false;
    }

    /** 
     * Notify this presenter that it should be started
     *
     * @param listUrl the listUrl for which the payment methods should be loaded
     */
    void onStart(String listUrl) {
        this.started = true;
        this.listUrl = listUrl;
    }

    private int nextListItemType() {
        return listItemType++;
    }
}
