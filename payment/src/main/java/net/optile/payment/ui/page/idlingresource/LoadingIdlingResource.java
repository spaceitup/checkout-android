/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page.idlingresource;

import java.util.concurrent.atomic.AtomicBoolean;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

/**
 * For waiting until the PaymentSession has been loaded
 */
public class LoadingIdlingResource implements IdlingResource {

    private volatile ResourceCallback callback;
    private AtomicBoolean isIdleNow = new AtomicBoolean(false);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        Log.i("pay", "registerIdleTransitionCallback");
        this.callback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        Log.i("pay", "setIdleState: " + isIdleNow);
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
