/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.example.shop.summary;

import static android.app.Activity.RESULT_CANCELED;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import java.util.concurrent.Callable;

import com.payoneer.mrs.example.shop.shared.ShopException;
import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.ListResult;
import com.payoneer.mrs.payment.model.PresetAccount;
import com.payoneer.mrs.payment.network.ListConnection;
import com.payoneer.mrs.payment.ui.PaymentActivityResult;
import com.payoneer.mrs.payment.ui.PaymentResult;

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
     * @param sdkResult the result received from the Android SDK
     */
    void handleSdkResult(PaymentActivityResult sdkResult) {
        switch (sdkResult.getRequestCode()) {
            case SummaryActivity.PAYMENT_REQUEST_CODE:
                handlePaymentResult(sdkResult);
                break;
            case SummaryActivity.EDIT_REQUEST_CODE:
                handleEditResult(sdkResult);
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
            public ListResult call() throws ShopException {
                return asyncLoadPaymentSession(listUrl);
            }
        });
        this.subscription = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleSubscriber<ListResult>() {
                @Override
                public void onSuccess(ListResult result) {
                    handleLoadPaymentSessionSuccess(result);
                }

                @Override
                public void onError(Throwable error) {
                    handleLoadPaymentSessionError(error);
                }
            });
    }

    private void handleEditResult(PaymentActivityResult result) {
        switch (result.getResultCode()) {
            case RESULT_CODE_ERROR:
                handlePaymentResultError(result.getPaymentResult());
                break;
            case RESULT_CANCELED:
                // This resultCode is returned when the user closed the payment page and there is no payment result available
            case RESULT_CODE_PROCEED:
                loadPaymentDetails(view.getListUrl());
                break;
        }
    }

    private void handlePaymentResult(PaymentActivityResult sdkResult) {
        PaymentResult paymentResult = sdkResult.getPaymentResult();
        switch (sdkResult.getResultCode()) {
            case RESULT_CODE_PROCEED:
                handlePaymentResultProceed(paymentResult);
                break;
            case RESULT_CODE_ERROR:
                handlePaymentResultError(paymentResult);
                break;
        }
    }

    private void handlePaymentResultProceed(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        if (interaction != null) {
            view.showPaymentConfirmation();
        }
    }

    private void handlePaymentResultError(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.ABORT:
                if (!result.isNetworkFailure()) {
                    view.stopPaymentWithErrorMessage();
                }
                break;
            case InteractionCode.VERIFY:
                // VERIFY means that a charge request has been made but the status of the payment could
                // not be verified by the Android-SDK, i.e. because of a network error
                view.stopPaymentWithErrorMessage();
                break;
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
            case InteractionCode.RELOAD:
            case InteractionCode.RETRY:
                view.showPaymentList();
                break;
        }
    }

    private boolean isLoadSessionActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    private void handleLoadPaymentSessionSuccess(ListResult result) {
        this.subscription = null;
        PresetAccount account = result.getPresetAccount();
        if (account == null) {
            view.close();
            return;
        }
        view.showPaymentDetails(account);
    }

    private void handleLoadPaymentSessionError(Throwable error) {
        this.subscription = null;
        view.stopPaymentWithErrorMessage();
    }

    private ListResult asyncLoadPaymentSession(String listUrl) throws ShopException {
        ListConnection conn = new ListConnection(view.getActivity());
        try {
            return conn.getListResult(listUrl);
        } catch (PaymentException e) {
            throw new ShopException("Error loading list result", e);
        }
    }
}
