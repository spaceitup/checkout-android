/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import net.optile.payment.core.PaymentException;
import net.optile.example.demo.R;
import net.optile.example.demo.shared.DemoException;
import net.optile.example.demo.shared.DemoSettings;
import net.optile.example.demo.shared.SdkResult;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.util.PaymentUtils;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * CheckoutPresenter takes care of communicating with the Payment API and instructing the CheckoutView to display the proper information.
 */
final class CheckoutPresenter {

    private final CheckoutView view;

    private Subscription subscription;
    
    /**
     * Construct a new CheckoutPresenter
     *
     * @param view the checkout view
     */
    CheckoutPresenter(CheckoutView view) {
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
     * Create a PaymentSession given the demo settings. Remind, merchant apps should never create PaymentSessions in their mobile apps.
     * 
     * @param settings indicating which type of PaymentSession needs to be created.
     */
    void createPaymentSession(DemoSettings settings) {

        if (isCreateSessionActive()) {
            return;
        }
        int resId = 0;

        if (settings.getSummary()) {
            resId = settings.getRegistered() ? R.raw.preset_registered : R.raw.preset_newuser;
        } else {
            resId = settings.getRegistered() ? R.raw.direct_registered : R.raw.direct_newuser;
        }
        Context context = view.getContext();

        try {
            final String url = context.getString(R.string.paymentapi_url);
            final String auth = context.getString(R.string.paymentapi_auth);
            final String listData = PaymentUtils.readRawResource(context.getResources(), resId);

            final Single<String> single = Single.fromCallable(new Callable<String>() {
                @Override
                public String call() throws DemoException {
                    return asyncCreatePaymentSession(url, auth, listData);

                }
            });
            this.subscription = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String listUrl) {
                        callbackPaymentSessionSuccess(listUrl);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callbackPaymentSessionError(error);
                    }
                });
        } catch (IOException e) {
            view.showPaymentError(context.getString(R.string.dialog_error_list));
        }
    }

    /**
     * Handle the received checkout result from the Android SDK.
     *
     * @param result the result received from the SDK
     */
    void handleSdkResult(SdkResult result) {

        switch (result.resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                view.showPaymentSuccess();
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handlePaymentCanceled(result.paymentResult);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                // Android SDK already shows errors to the user so
                // we ignore this state.
                break;
        }
    }

    private void handlePaymentCanceled(PaymentResult result) {
        Interaction interaction = result.getInteraction();

        if (interaction != null && interaction.getCode() == InteractionCode.ABORT) {
            view.closePayment();
        }
    }

    /**
     * Check if the presenter is creating a new payment session.
     *
     * @return true when creating a payment session, false otherwise
     */
    private boolean isCreateSessionActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    private void callbackPaymentSessionSuccess(String listUrl) {
        this.subscription = null;
        view.openPaymentPage(listUrl);
    }

    private void callbackPaymentSessionError(Throwable error) {
        this.subscription = null;
        view.showPaymentError(error.toString());
    }

    private String asyncCreatePaymentSession(String url, String authorization, String listData) throws DemoException {
        ListConnection conn = new ListConnection();

        try {
            ListResult result = conn.createPaymentSession(url, authorization, listData);
            URL selfUrl = getSelfUrl(result.getLinks());

            if (selfUrl == null) {
                throw new DemoException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            throw new DemoException("Error creating payment session", e);
        }
    }

    private URL getSelfUrl(Map<String, URL> links) {
        return links != null ? links.get("self") : null;
    }
}
