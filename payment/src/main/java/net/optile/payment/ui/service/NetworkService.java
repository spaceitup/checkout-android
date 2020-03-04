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
import net.optile.payment.model.OperationResult;

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
     * @param activity handles the payment that should be prepared
     * @param requestCode should be returned to the presenter when the payment is prepared
     * @param operation that should be prepared
     */
    public void preparePayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
    }

    /**
     * Process the payment through this NetworkService. The result is either returned through the onActivityResult call in the
     * provided Activity or through the NetworkServicePresenter.
     *
     * @param activity handles the payment that should be processed
     * @param requestCode should be returned to the presenter when the payment is processed
     * @param operation that should be processed
     */
    public void processPayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
    }

    /** 
     * Notify the network service that the payment has been redirected and an operation result has been received. 
     * 
     * @param result containing the result of the operation, may be null if the redirect has been terminated 
     *        without receiving an OperationResult from the backend
     */
    public void onRedirectSuccess(OperationResult result) {
    }
    
    /** 
     * Notify the network service that the redirect has failed to result in an OperationResult from the backend.
     * The network service should handle this situation and make sure the NetworkServicePresenter is notified with the 
     * appropiate PaymentResult.
     */
    public void onRedirectCanceled() {
    }
}
