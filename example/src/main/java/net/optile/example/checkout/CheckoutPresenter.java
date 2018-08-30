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

import android.util.Log;
import java.util.List;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import java.util.concurrent.Callable;

/**
 * CheckoutPresenter responsible for communicating with the 
 * Payment SDK
 */
class CheckoutPresenter {

    public final static String TAG = "payment_CheckoutPresenter";

    private CheckoutView view;

    private Subscription subscription;
    
    /**
     * Construct a new CheckoutPresenter
     */
    CheckoutPresenter(CheckoutView view) {
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
    boolean isListRequestActive() {
        return subscription != null && !subscription.isUnsubscribed();
    }
    
    /** 
     * Make a list request, normally mobile apps using the 
     * Android Payment SDK do not make list requests. 
     * Instead the merchant backend sends this request to 
     * the Payment API.
     *
     * @param authorization     The authorization header for the list request
     */
    void newListRequest(final String authorization) {

        if (isListRequestActive()) {
            return;
        }

        Single<Void> single = Single.fromCallable(new Callable<Void>() {

                @Override
                public Void call() throws CheckoutException {
                    handleNewListRequest(authorization);
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

    private void handleNewListRequest(String authorization) throws CheckoutException {
        
    }
}
