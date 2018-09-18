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

import net.optile.payment.util.PaymentUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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

    void refresh(final String listUrl) {
        //loadLocalListResult();
        test();
    }

    private int nextListItemType() {
        return listItemType++;
    }

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
    
    private void loadLocalListResult() {
        try {
            final String data = PaymentUtils.readRawResource(view.getContext().getResources(), R.raw.listresult);
            final Gson gson = new GsonBuilder().create();
            final ListResult result = gson.fromJson(data, ListResult.class); 
            handleListResult(result);
        } catch (JsonParseException e) {
            Log.wtf(TAG, e);
        }
    }

    private void test() {
        executeTest(7000);
        executeTest(5000);
        executeTest(4000);
        executeTest(2000);
    }

    private void executeTest(final int sleep) {

        WorkerTask<String> task = WorkerTask.fromCallable(new Callable<String>() {
                @Override
                public String call() throws PaymentException {
                    return loadListResult(sleep);
                }
            });

        task.subscribe(new WorkerSubscriber<String>() {

                @Override
                public void onSuccess(String listUrl) {
                    Log.i(TAG, "oSuccess: " + listUrl);
                    //view.openPaymentPage(listUrl);
                }

                @Override
                public void onError(Throwable error) {
                    Log.i(TAG, "onError: " + error);
                }
            });
        Workers.getInstance().forNetworkTasks().execute(task); 
    }

    private String loadListResult(int sleep) throws PaymentException {
        Log.i(TAG, "starting: " + sleep);

        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
        }
        return "done: " + sleep;
    }
}
