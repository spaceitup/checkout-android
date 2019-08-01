/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import android.app.Activity;
import net.optile.payment.form.Operation;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.model.PaymentSession;

/**
 * Interface for PaymentNetworkServices, a NetworkService is responsible for activating and
 * finalizing a payment through the supported network.
 */
public abstract class NetworkService {

    protected NetworkServicePresenter presenter;
    
    /** 
     * Stop this NetworkService, i.e. if the payment method is currently being preset
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
     * onActivityResult or a direct call into the presenter. 
     *
     * @param activity 
     * @param requestCode
     * @param session
     * @param operation
     */
    public void preparePayment(Activity activity, int requestCode, PaymentSession session, Operation operation) {
    }
    
    /** 
     * Process the payment through this NetworkService. Depending on the type of operation (preset, update, activate, etc) the NetworkService can 
     * decide how to present the processing to the end-user. This means that results are either returned through the onActivityResult call in the 
     * provided Activity or through the NetworkServicePresenter.
     * 
     * @param activity 
     * @param requestCode
     * @param session
     * @param operation
     */
    public void processPayment(Activity activity, int requestCode, PaymentSession session, Operation operation) {
    }
}
