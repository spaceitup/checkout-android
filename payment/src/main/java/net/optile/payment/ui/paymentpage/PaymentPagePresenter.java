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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.network.ErrorDetails;
import net.optile.payment.network.ListConnection;
import net.optile.payment.network.NetworkException;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "pay_PayPresenter";

    private final ListConnection listConnection;

    private final PaymentPageView view;

    private PaymentSession session;

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
        asyncLoadPaymentSession(listUrl);
    }

    private int nextGroupType() {
        return groupType++;
    }

    private void handleSuccess(PaymentSession session) {
        view.showLoading(false);
        this.session = session;
        handleInteraction(session.listResult.getInteraction());
    }

    private void handleError(Throwable error) {
        view.showLoading(false);

        if (error instanceof NetworkException) {
            handleNetworkError((NetworkException) error);
            return;
        }
        // TODO: Handle unrecoverable errors
        Log.wtf(TAG, error);
    }

    private void handleNetworkError(NetworkException exception) {
        ErrorDetails details = exception.details;

        if (details.errorInfo != null) {
            Interaction interaction = details.errorInfo.getInteraction();
            String code = interaction.getCode();
            String reason = interaction.getReason();
            view.abortPayment(code, reason, translateInteraction(code, reason));
            return;
        }
        // TODO: Handle the rest of the errors in the ErrorDetails
        Log.e(TAG, details.toString());
    }

    private void handleInteraction(Interaction interaction) {
        String code = interaction.getCode();
        String reason = interaction.getReason();

        if (!InteractionCode.isValid(code)) {
            view.abortPayment(code, reason, "Unknown interaction code received");
            return;
        }
        switch (code) {
            case InteractionCode.PROCEED:
                handleStateProceed();
                break;
            case InteractionCode.ABORT:
            case InteractionCode.TRY_OTHER_NETWORK:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.RETRY:
            case InteractionCode.RELOAD:
                view.abortPayment(code, reason, translateInteraction(code, reason));
        }
    }

    private void handleStateProceed() {
        if (session.getApplicableNetworkSize() == 0) {
            view.showCenterMessage(R.string.error_paymentpage_empty);
        } else if (session.groups.size() == 0) {
            view.showCenterMessage(R.string.error_paymentpage_notsupported);
        }
        view.showPaymentSession(session);
    }

    private String translateInteraction(String code, String reason) {
        StringBuilder sb = new StringBuilder("interaction.");
        sb.append(code).append(".").append(reason);
        return session.translate(sb.toString(), sb.toString());
    }

    private void asyncLoadPaymentSession(final String listUrl) {

        WorkerTask<PaymentSession> task = WorkerTask.fromCallable(new Callable<PaymentSession>() {
            @Override
            public PaymentSession call() throws NetworkException, PaymentException {
                return loadPaymentSession(listUrl);
            }
        });
        task.subscribe(new WorkerSubscriber<PaymentSession>() {
            @Override
            public void onSuccess(PaymentSession paymentSession) {
                handleSuccess(paymentSession);
            }

            @Override
            public void onError(Throwable error) {
                handleError(error);
            }
        });
        view.hideCenterMessage();
        view.clear();
        view.showLoading(true);
        Workers.getInstance().forNetworkTasks().execute(task);
    }

    private PaymentSession loadPaymentSession(String listUrl) throws NetworkException, PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);
        List<PaymentItem> items = loadPaymentItems(listResult);
        List<PaymentGroup> groups = new ArrayList<>();

        int selIndex = -1;
        int index = 0;
        for (PaymentItem item : items) {
            PaymentGroup group = createPaymentGroup(item);
            if (selIndex == -1 && group.isSelected()) {
                selIndex = index;
            }
            groups.add(group);
            index++;
        }
        selIndex = selIndex == -1 ? 0 : selIndex;
        PaymentSession session = new PaymentSession(listResult, groups, selIndex);
        session.setLanguage(loadPageLanguage(items));
        return session;
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

    private PaymentGroup createPaymentGroup(PaymentItem item) {
        return new PaymentGroup(nextGroupType(), item, item.getInputElements());
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
        String button = network.getButton();
        return (TextUtils.isEmpty(button) || !button.contains("activate")) && !network.getRedirect();
    }
}
