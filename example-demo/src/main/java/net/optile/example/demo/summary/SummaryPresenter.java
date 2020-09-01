/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.summary;

import java.util.List;
import java.util.concurrent.Callable;

import net.optile.example.demo.shared.DemoException;
import net.optile.example.demo.shared.SdkResult;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.network.ListConnection;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * SummaryPresenter responsible for communicating with the
 * Android SDK and informing the SummaryView to show content to the user.
 */
final class SummaryPresenter {

    private SummaryView view;
    private Subscription subscription;
    private ListResult result;

    /**
     * Construct a new SummaryPresenter
     *
     * @param view the view
     */
    SummaryPresenter(SummaryView view) {
        this.view = view;
    }

    /**
     * Notify the presenter that it should be stopped.
     * Check if there are any pending subscriptions and unsubscribe if needed
     */
    void onStop() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    /**
     * Handle the received checkout result from the Android SDK.
     *
     * @param result the result received from the SDK
     */
    void handleSdkResult(SdkResult result) {
        switch (result.requestCode) {
            case SummaryActivity.PAYMENT_REQUEST_CODE:
                handlePaymentResult(result);
                break;
            case SummaryActivity.EDIT_REQUEST_CODE:
                handleEditResult(result);
                break;
        }
    }

    void loadPaymentDetails(String listUrl) {
        if (isLoadSessionActive()) {
            return;
        }
        view.showLoading(true);

        final Single<ListResult> single = Single.fromCallable(new Callable<ListResult>() {
            @Override
            public ListResult call() throws DemoException {
                return asyncLoadPaymentSession(listUrl);
            }
        });
        this.subscription = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleSubscriber<ListResult>() {
                @Override
                public void onSuccess(ListResult result) {
                    callbackLoadPaymentSessionSuccess(result);
                }

                @Override
                public void onError(Throwable error) {
                    callbackLoadPaymentSessionError(error);
                }
            });
    }

    private void handleEditResult(SdkResult result) {
        switch (result.resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                loadPaymentDetails(view.getListUrl());
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handleResultCanceled(result.paymentResult);
                break;
        }
    }

    private void handlePaymentResult(SdkResult result) {
        switch (result.resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                handleResultOk(result.paymentResult);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handleResultCanceled(result.paymentResult);
                break;
        }
    }

    private void handleResultOk(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        if (interaction != null) {
            view.showConfirmScreen();
        }
    }

    private void handleResultCanceled(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.ABORT:
                if (!result.hasNetworkFailureError()) {
                    view.closePayment(null);
                }
                break;
            case InteractionCode.VERIFY:
                // VERIFY means that a charge request has been made but the status of the payment could
                // not be verified by the Android-SDK, i.e. because of a network error
                view.closePayment(null);
                break;
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
            case InteractionCode.RELOAD:
            case InteractionCode.RETRY:
                view.showPaymentListScreen();
                break;
        }
    }

    private boolean isLoadSessionActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    private void callbackLoadPaymentSessionSuccess(ListResult result) {
        this.subscription = null;
        this.result = result;
        PresetAccount account = result.getPresetAccount();
        String paymentMethod = getPaymentMethod(account, result);
        if (paymentMethod == null) {
            view.closeScreen();
            return;
        }
        view.showPaymentDetails(account, paymentMethod);
    }

    private void callbackLoadPaymentSessionError(Throwable error) {
        this.subscription = null;
        this.result = null;
        view.closePayment(error.toString());
    }

    private ListResult asyncLoadPaymentSession(String listUrl) throws DemoException {
        ListConnection conn = new ListConnection();
        try {
            return conn.getListResult(listUrl);
        } catch (PaymentException e) {
            throw new DemoException("Error loading payment session", e);
        }
    }

    private String getPaymentMethod(PresetAccount account, ListResult listResult) {
        Networks networks = listResult.getNetworks();
        if (account == null || networks == null) {
            return null;
        }
        List<ApplicableNetwork> an = networks.getApplicable();
        if (an == null || an.size() == 0) {
            return null;
        }
        String code = account.getCode();
        for (ApplicableNetwork network : an) {
            if (network.getCode().equals(code)) {
                return network.getMethod();
            }
        }
        return null;
    }
}
