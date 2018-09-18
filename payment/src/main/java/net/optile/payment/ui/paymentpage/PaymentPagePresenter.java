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
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.Networks;

import net.optile.payment.core.Workers;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.PaymentException;

import net.optile.payment.network.NetworkException;
import net.optile.payment.network.ListConnection;

import net.optile.payment.util.PaymentUtils;

import java.util.concurrent.Callable;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "pay_PayPresenter";

    private PaymentPageView view;

    private boolean started;

    private int listItemType;

    /**
     * Create a new PaymentPagePresenter
     *
     * @param view The PaymentPageView displaying the payment methods
     */
    PaymentPagePresenter(final PaymentPageView view) {
        this.view = view;
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
    void onStart() {
        this.started = true;
    }

    /** 
     * Refresh the ListResult, this will result in reloading the ListResult and language file
     * 
     * @param listUrl the url pointing to the ListResult in the Payment API
     */
    void refresh(final String listUrl) {
        asyncLoadListResult(listUrl);
    }

    private int nextListItemType() {
        return listItemType++;
    }

    /** 
     * Handle the incoming list result
     * 
     * @param result 
     */
    private void handleListResult(ListResult result) {
        Interaction interaction = result.getInteraction();
        String code = interaction.getCode();

        if (!InteractionCode.isValid(code)) {
            view.abortPayment(code, "Unknown interaction code received from the Payment API");
            return;
        }
        switch (code) {
        case InteractionCode.PROCEED:
            handleProceed(result);
            break;
        case InteractionCode.ABORT:
        case InteractionCode.TRY_OTHER_NETWORK:
        case InteractionCode.TRY_OTHER_ACCOUNT:
        case InteractionCode.RETRY:
        case InteractionCode.RELOAD:
            view.abortPayment(code, interaction.getReason());
        }
    }

    private void handleProceed(ListResult result) {
        List<PaymentListItem> items = new ArrayList<PaymentListItem>();
        Networks nw = result.getNetworks();
        if (nw == null) {
            view.setItems(items);
            return;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        if (an == null || an.size() == 0) {
            view.setItems(items);
            return;
        }
        showApplicableNetworks(an);
    }

    private void showApplicableNetworks(final List<ApplicableNetwork> networks) {
        List<PaymentListItem> items = new ArrayList<PaymentListItem>();

        for (ApplicableNetwork network : networks) {
            items.add(createPaymentListItem(network));
        }
        view.setItems(items);
    }

    private PaymentListItem createPaymentListItem(final ApplicableNetwork network) {
        return new PaymentListItem(nextListItemType(), network);
    }
    
    private void asyncLoadListResult(final String listUrl) {

        WorkerTask<ListResult> task = WorkerTask.fromCallable(new Callable<ListResult>() {
                @Override
                public ListResult call() throws NetworkException {
                    return loadListResult(listUrl);
                }
            });

        task.subscribe(new WorkerSubscriber<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    handleListResult(listResult);
                }
                @Override
                public void onError(Throwable error) {
                    Log.i(TAG, "onError: " + error);
                }
            });
        Workers.getInstance().forNetworkTasks().execute(task); 
    }

    private ListResult loadListResult(String listUrl) throws NetworkException {
        ListConnection conn = new ListConnection();
        return conn.getListResult(listUrl);
    }
}
