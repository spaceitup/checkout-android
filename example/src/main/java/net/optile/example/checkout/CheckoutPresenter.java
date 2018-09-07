/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example.checkout;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import android.util.Log;
import net.optile.example.R;
import net.optile.example.util.AppUtils;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;
import net.optile.payment.network.ChargeConnection;
import net.optile.payment.network.ListConnection;
import net.optile.payment.network.NetworkException;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * CheckoutPresenter responsible for communicating with the
 * Payment SDK
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
     * Is this presenter currently making a new list request
     *
     * @return true when active, false otherwise
     */
    boolean isCreateListSessionActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }

    /**
     * Initiate a checkout request in the mobile app.
     *
     * @param context The context needed to obtain system resources
     */
    void checkout(final Context context) {

        if (isCreateListSessionActive()) {
            return;
        }

        final String url = context.getString(R.string.url);
        final String auth = context.getString(R.string.payment_authorization);

        final String listData = AppUtils.readRawResource(context.getResources(), R.raw.list);
        final String chargeData = AppUtils.readRawResource(context.getResources(), R.raw.charge);

        final Single<Void> single = Single.fromCallable(new Callable<Void>() {

            @Override
            public Void call() throws CheckoutException {
                test(url, auth, listData, chargeData);
                return null;
            }
        });

        this.subscription = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleSubscriber<Void>() {

                @Override
                public void onSuccess(Void param) {
                    Log.i(TAG, "onSuccess");
                }

                @Override
                public void onError(Throwable error) {
                    Log.i(TAG, "onError: " + error);
                }
            });
    }

    /**
     * REMIND, this code must be removed later. It is only used for testing the
     * SDK during development.
     *
     * @param url
     * @param authorization
     * @param listData
     * @param chargeData
     */
    private void test(String url, String authorization, String listData, String chargeData) throws CheckoutException {
        ListConnection conn = new ListConnection(url);

        try {
            ListResult result = conn.createPaymentSession(authorization, listData);
            Map<String, URL> links = result.getLinks();
            URL selfURL = links.get("self");

            // Test the self URL and load list session
            if (selfURL != null) {
                result = conn.getListResult(selfURL);
            }

            // Test a charge request
            List<ApplicableNetwork> networks = result.getNetworks().getApplicable();
            String code = null;

            for (ApplicableNetwork network : networks) {

                if (network.getCode().equals("CARTEBLEUE")) {
                    testChargeRequest(network, chargeData);
                }
            }
        } catch (NetworkException e) {
            Log.i(TAG, "NetworkException: " + e.details);
            e.printStackTrace();
        }
    }

    /**
     * REMIND, this code must be removed later. It is only used for testing the
     * SDK during development.
     *
     * @param network
     * @param chargeData
     */
    private void testChargeRequest(ApplicableNetwork network, String chargeData) throws NetworkException {

        Log.i(TAG, "testChargeRequest Network[" + network.getCode() + ", " + network.getLabel() + "]");

        Map<String, URL> links = network.getLinks();
        URL url = links.get("operation");

        ChargeConnection conn = new ChargeConnection();
        OperationResult result = conn.createCharge(url, chargeData);
        Redirect redirect = result.getRedirect();

        Log.i(TAG, "Charge response: " + redirect.getCheckedMethod());
    }
}
