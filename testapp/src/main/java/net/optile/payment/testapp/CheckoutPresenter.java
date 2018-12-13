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

package net.optile.payment.testapp;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import android.util.Log;
import net.optile.example.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;
import net.optile.payment.util.PaymentUtils;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * CheckoutPresenter responsible for communicating with the
 * Payment SDK and informing the CheckoutView to show content to the user.
 */
final class CheckoutPresenter {

    private static String TAG = "payment_CheckoutPresenter";

    private CheckoutView view;

    private Subscription subscription;

    /**
     * Construct a new CheckoutPresenter
     *
     * @param view the view
     */
    CheckoutPresenter(final CheckoutView view) {
        this.view = view;
    }

    /**
     * Notify the presenter that it should be stopped.
     * Check if there are any pending subscriptions and unsubscribe if needed
     */
    public void onStop() {

        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    /**
     * Handle the received checkout result from the optile Payment SDK.
     *
     * @param result the result received from the SDK
     */
    void handleCheckoutResult(CheckoutResult result) {

        if (result.success) {
            view.showPaymentSuccess();
        } else if (result.paymentResult != null) {
            Log.i(TAG, "CheckoutError[" + result.paymentResult + "]");
        } else {
            Log.i(TAG, "CheckoutError[unknown]");
        }
    }

    /**
     * Check if the presenter is creating a new payment session.
     *
     * @return true when creating a payment session, false otherwise
     */
    private boolean isCreatePaymentSessionActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    private void callbackPaymentSessionSuccess(String listUrl) {
        this.subscription = null;
        view.openPaymentPage(listUrl);
    }

    private void callbackPaymentSessionError(Throwable error) {
        this.subscription = null;
        view.showPaymentError(error.toString());
        Log.wtf(TAG, error);
    }

    /**
     * REMIND, this code must NOT be used in production apps. 
     * Mobile apps using the Android Payment SDK should not create PaymentSessions themselves. 
     * Creating PaymentSessions must only be performed by the backend of the merchant.
     *
     * In order to inject the authorization value in this example app, a System Environment variable must be set.
     * The build.gradle file will add this environment variable automatically into the strings.xml file during compilation.
     * Add the following entry in your .bash_profile.
     * 
     * export PAYMENT_AUTHORIZATION='Basic ...Authorization Base64 value...'
     *
     * @param context needed to obtain string values from the strings.xml resources file
     */
    void createPaymentSession(final Context context) {

        if (isCreatePaymentSessionActive()) {
            return;
        }
        final String url = context.getString(R.string.url);
        final String auth = context.getString(R.string.payment_authorization);
        final String listData = PaymentUtils.readRawResource(context.getResources(), R.raw.list);

        final Single<String> single = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws CheckoutException {
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
    }

    private String asyncCreatePaymentSession(String url, String authorization, String listData) throws CheckoutException {
        ListConnection conn = new ListConnection();
        try {
            ListResult result = conn.createPaymentSession(url, authorization, listData);
            URL selfUrl = getSelfUrl(result.getLinks());
            if (selfUrl == null) {
                throw new CheckoutException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            Log.i(TAG, e.error.toString());
            Log.wtf(TAG, e);
            throw new CheckoutException("Error creating payment session", e);
        }
    }

    private URL getSelfUrl(Map<String, URL> links) {
        return links != null ? links.get("self") : null;
    }
}
