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

package net.optile.payment.ui.internal;

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
     * The list of payment methods
     */
    private List<SearchItem> items;

    /**
     * Create a new SearchPresenter
     */
    SearchPresenter(SearchView view) {
        this.view = view;
        this.curPage = -1;
        this.items = new ArrayList<SearchItem>();
    }

    /** 
     * Notify this presenter that it should be stopped
     */
    public void onStop() {
    }

    /** 
     * Notify this presenter that it should be started
     *
     * @param listUrl the listUrl for which the payment methods should be loaded
     */
    public void onStart(String listUrl) {

        if (!listUrl.equals(this.listUrl)) {
            // REMIND: clear the previously loaded ListResult and clear all list elements
        }
        this.listUrl = listUrl;
    }
}
