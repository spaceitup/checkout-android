/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service.basic;

import android.app.Activity;
import android.content.Intent;
import net.optile.payment.R;
import net.optile.payment.form.Operation;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.page.ProcessPaymentActivity;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.OperationListener;
import net.optile.payment.ui.service.OperationService;

/**
 * BasicNetworkService implementing the handling of basic payment methods like Visa, Mastercard and Sepa
 */
public final class BasicNetworkService extends NetworkService implements OperationListener {
    private final OperationService service;

    /**
     * Create a new BasicNetworkService, this service is a basic implementation that simply send an operation to the Payment API.
     */
    public BasicNetworkService() {
        service = new OperationService();
        service.setListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        service.stop();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void preparePayment(Activity activity, int requestCode, PaymentSession session, Operation operation) {
        if (presenter != null) {
            //presenter.onPreparePaymentSuccess(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processPayment(Activity activity, int requestCode, PaymentSession session, Operation operation) {
        String type = operation.getType();

        if (Operation.CHARGE.equals(type)) {
            // Redirect to the ProcessPaymentActivity to process the operation request
            Intent intent = ProcessPaymentActivity.createStartIntent(activity, operation);
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(ProcessPaymentActivity.getStartTransition(), R.anim.no_animation);
        } else {
            presenter.showProgress();
            service.postOperation(operation);
        }
    }

    @Override
    public void onOperationSuccess(final OperationResult operationResult) {
        if (presenter != null) {
            //presenter.onProcessPaymentSuccess(operationResult);
        }
    }

    @Override
    public void onOperationError(final Throwable cause) {
        if (presenter != null) {
            //presenter.onProcessPaymentError(cause);
        }
    }
}
