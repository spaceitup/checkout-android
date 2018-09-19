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
import java.util.Map;
import java.util.Properties;
import java.net.URL;

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

    private final ListConnection listConnection;

    private final PaymentPageView view;

    private boolean started;

    private int listItemType;

    
    /**
     * Create a new PaymentPagePresenter
     *
     * @param view The PaymentPageView displaying the payment methods
     */
    PaymentPagePresenter(final PaymentPageView view) {
        this.view = view;
        this.listConnection = new ListConnection();
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
        asyncLoadPayment(listUrl);
    }

    private int nextListItemType() {
        return listItemType++;
    }

    private void handlePayment(final PaymentHolder holder) {
        Interaction interaction = holder.listResult.getInteraction();
        String code = interaction.getCode();

        if (!InteractionCode.isValid(code)) {
            view.abortPayment(code, "Unknown interaction code received from the Payment API");
            return;
        }
        switch (code) {
        case InteractionCode.PROCEED:
            handleStateProceed(holder);
            break;
        case InteractionCode.ABORT:
        case InteractionCode.TRY_OTHER_NETWORK:
        case InteractionCode.TRY_OTHER_ACCOUNT:
        case InteractionCode.RETRY:
        case InteractionCode.RELOAD:
            view.abortPayment(code, interaction.getReason());
        }
    }

    private void handleStateProceed(final PaymentHolder holder) {
        List<PaymentListItem> items = new ArrayList<PaymentListItem>();

        for (PaymentMethod method : holder.methods) {
            items.add(createPaymentListItem(method));
        }
        view.setItems(items);
    }

    private PaymentListItem createPaymentListItem(final PaymentMethod method) {
        return new PaymentListItem(nextListItemType(), method);
    }
    
    private void asyncLoadPayment(final String listUrl) {

        WorkerTask<PaymentHolder> task = WorkerTask.fromCallable(new Callable<PaymentHolder>() {
                @Override
                public PaymentHolder call() throws PaymentException {
                    return loadPayment(listUrl);
                }
            });

        task.subscribe(new WorkerSubscriber<PaymentHolder>() {
                @Override
                public void onSuccess(PaymentHolder holder) {
                    handlePayment(holder);
                }
                @Override
                public void onError(Throwable error) {
                    Log.i(TAG, "onError: " + error);
                }
            });
        Workers.getInstance().forNetworkTasks().execute(task); 
    }

    private PaymentHolder loadPayment(final String listUrl) throws PaymentException {
        try {
            ListResult listResult = listConnection.getListResult(listUrl);
            List<PaymentMethod> methods = loadPaymentMethods(listResult);
            return new PaymentHolder(listResult, methods);
        } catch (NetworkException e) {
            throw new PaymentException("PaymentPagePresenter[loadPayment]", e);
        }
    }
    
    private List<PaymentMethod> loadPaymentMethods(final ListResult listResult) throws PaymentException, NetworkException {
        List<PaymentMethod> methods = new ArrayList<PaymentMethod>();
        Networks nw = listResult.getNetworks();
        if (nw == null) {
            return methods;
        }
        List<ApplicableNetwork> an = nw.getApplicable();
        if (an == null || an.size() == 0) {
            return methods;
        }
        for (ApplicableNetwork network : an) {
            methods.add(new PaymentMethod(network, loadNetworkLanguage(network)));
        }
        return methods;
    }

    private Properties loadNetworkLanguage(final ApplicableNetwork network) throws PaymentException, NetworkException {
        Map<String, URL> links = network.getLinks();
        URL langUrl = null;

        if (links == null || (langUrl = links.get("lang")) == null) {
            throw new PaymentException("Error loading language, missing 'lang' url");
        }
        return listConnection.getLanguage(langUrl);
    }
}
