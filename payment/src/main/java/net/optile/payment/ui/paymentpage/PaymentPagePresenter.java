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

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import net.optile.payment.R;
import net.optile.payment.model.ListResult;
import net.optile.payment.util.PaymentUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.Resources;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "pay_PayPresenter";

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
     * Notify this presenter that it should start
     *
     * @param listUrl the listUrl for which the payment methods should be loaded
     */
    void onStart(String listUrl) {
        this.started = true;
        this.listUrl = listUrl;
        loadListResult();
    }

    private int nextListItemType() {
        return listItemType++;
    }

    private void loadListResult() {

        try {
            final String data = PaymentUtils.readRawResource(view.getContext().getResources(), R.raw.listresult);
            Log.i(TAG, "data: " + data.length());
            Gson gson = new GsonBuilder().create();
            ListResult result = gson.fromJson(data, ListResult.class);
        } catch (JsonParseException e) {
            Log.wtf(TAG, e);
        }
    }
}
