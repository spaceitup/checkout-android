/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import android.app.Activity;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;

/**
 * Interface for network services, a NetworkService is responsible for activating and
 * processing a payment through the supported payment network.
 */
public abstract class NetworkService {

    protected NetworkServicePresenter presenter;

    /**
     * Stop this NetworkService
     */
    public void stop() {
    }

    /**
     * Set the presenter in this NetworkService
     *
     * @param presenter the presenter to be set
     */
    public void setPresenter(NetworkServicePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Prepare the payment for this NetworkService. Depending on the type of network (Visa, GooglePay) the result may either be returned through the
     * onActivityResult or through the NetworkServicePresenter.
     *
     * @param activity
     * @param requestCode
     * @param operation
     */
    public void preparePayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
    }

    /**
     * Process the payment through this NetworkService. The result is either returned through the onActivityResult call in the
     * provided Activity or through the NetworkServicePresenter.
     *
     * @param activity
     * @param requestCode
     * @param operation
     */
    public void processPayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
    }
}
