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

import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import net.optile.payment.R;
import android.util.Log;
import android.text.TextUtils;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.network.ListConnection;
import net.optile.payment.network.NetworkException;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "pay_PayPresenter";
    
    private final ListConnection listConnection;

    private final PaymentPageView view;

    private PaymentHolder paymentHolder;
    
    private boolean started;

    private int groupType;

    /**
     * Create a new PaymentPagePresenter
     *
     * @param view The PaymentPageView displaying the payment items
     */
    PaymentPagePresenter(PaymentPageView view) {
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
     */
    void onStart() {
        this.started = true;
    }

    /** 
     * Refresh the ListResult, this will result in reloading the ListResult and language file
     * 
     * @param listUrl the url pointing to the ListResult in the Payment API
     */
    void refresh(String listUrl) {
        asyncLoadPayment(listUrl);
    }

    String translate(String key, final String defValue) {
        return paymentHolder != null ? paymentHolder.translate(key, defValue) : defValue;
    }
    
    private int nextGroupType() {
        return groupType++;
    }

    private void handlePayment(PaymentHolder paymentHolder) {
        this.paymentHolder = paymentHolder;
        Interaction interaction = paymentHolder.listResult.getInteraction();
        String code = interaction.getCode();

        if (!InteractionCode.isValid(code)) {
            view.abortPayment(code, "Unknown interaction code received from the Payment API");
            return;
        }
        switch (code) {
        case InteractionCode.PROCEED:
            handleStateProceed(paymentHolder);
            break;
        case InteractionCode.ABORT:
        case InteractionCode.TRY_OTHER_NETWORK:
        case InteractionCode.TRY_OTHER_ACCOUNT:
        case InteractionCode.RETRY:
        case InteractionCode.RELOAD:
            view.abortPayment(code, interaction.getReason());
        }
    }

    private void handleStateProceed(PaymentHolder holder) {
        List<PaymentGroup> items = new ArrayList<>();

        for (PaymentItem item : holder.items) {
            items.add(createPaymentGroup(item));
        }
        if (items.size() == 0) {
            view.showCenterMessage(R.string.error_paymentpage_empty);
        }
        view.setItems(items);
    }

    private PaymentGroup createPaymentGroup(PaymentItem item) {
        return new PaymentGroup(nextGroupType(), item, item.getInputElements());
    }
    
    private void asyncLoadPayment(String listUrl) {

        WorkerTask<PaymentHolder> task = WorkerTask.fromCallable(new Callable<PaymentHolder>() {
                @Override
                public PaymentHolder call() throws PaymentException {
                    return loadPayment(listUrl);
                }
            });
        task.subscribe(new WorkerSubscriber<PaymentHolder>() {
                @Override
                public void onSuccess(PaymentHolder holder) {
                    view.showLoading(false);
                    handlePayment(holder);
                }
                @Override
                public void onError(Throwable error) {
                    view.showLoading(false);
                    Log.i(TAG, "onError: " + error);
                }
            });

        view.hideCenterMessage();
        view.clearItems();
        view.showLoading(true);
        Workers.getInstance().forNetworkTasks().execute(task); 
    }

    private PaymentHolder loadPayment(String listUrl) throws PaymentException {
        try {
            ListResult listResult = listConnection.getListResult(listUrl);
            PaymentHolder holder = new PaymentHolder(listResult, loadPaymentItems(listResult));
            holder.setLanguage(loadPageLanguage(holder.items));
            return holder;
        } catch (NetworkException e) {
            throw new PaymentException("PaymentPagePresenter[loadPayment]", e);
        }
    }
    
    private List<PaymentItem> loadPaymentItems(ListResult listResult) throws NetworkException, PaymentException {
        List<PaymentItem> items = new ArrayList<>();
        Networks nw = listResult.getNetworks();

        if (nw == null) {
            return items;
        }
        List<ApplicableNetwork> an = nw.getApplicable();

        if (an == null || an.size() == 0) {
            return items;
        }
        for (ApplicableNetwork network : an) {
            if (isSupported(network)) {
                items.add(loadPaymentItem(network));
            }
        }
        return items;
    }

    private PaymentItem loadPaymentItem(ApplicableNetwork network) throws NetworkException, PaymentException {
        PaymentItem item = new PaymentItem(network);
        URL langUrl = item.getLink("lang");

        if (langUrl == null) {
            throw new PaymentException("Error loading network language, missing 'lang' link in ApplicableNetwork");            
        }
        item.setLanguage(listConnection.getLanguage(langUrl, new Properties()));
        return item;
    }

    /** 
     * This method loads the payment page language file. 
     * The URL for the paymentpage language file is constructed from the URL of one of the ApplicableNetwork entries.
     *
     * @param items contains the list of PaymentItem elements
     * @return the properties object containing the language entries 
     */
    private Properties loadPageLanguage(List<PaymentItem> items) throws NetworkException, PaymentException {
        Properties prop = new Properties();

        if (items.size() == 0) {
            return prop;
        }
        PaymentItem item = items.get(0);
        URL langUrl = item.getLink("lang");
        
        if (langUrl == null) {
            throw new PaymentException("Error loading payment page language, missing 'lang' link in ApplicableNetwork");
        }
        try {
            String newUrl = langUrl.toString().replaceAll(item.getCode(), "paymentpage");
            langUrl = new URL(newUrl);
            return listConnection.getLanguage(langUrl, prop);
        } catch (MalformedURLException e) {
            throw new PaymentException("PaymentPagePresenter[loadPageLanguage]", e);
        }
    }
    
    private boolean isSupported(ApplicableNetwork network) {

        // Check if the button is an activate button
        String button = network.getButton();
        return (TextUtils.isEmpty(button) || !button.contains("activate")) && !network.getRedirect();
    }
}
