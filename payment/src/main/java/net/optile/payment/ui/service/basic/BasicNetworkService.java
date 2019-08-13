/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service.basic;

import android.app.Activity;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.OperationResult;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
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
    public void preparePayment(Activity activity, int requestCode, Operation operation) {
        PaymentResult result = new PaymentResult("preparePayment not required");
        presenter.onPreparePaymentResult(PaymentUI.RESULT_CODE_OK, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processPayment(Activity activity, int requestCode, Operation operation) {
        presenter.showProgress();
        service.postOperation(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationSuccess(OperationResult operation) {
        PaymentResult result = new PaymentResult(operation);

        switch (operation.getInteraction().getCode()) {
            case InteractionCode.PROCEED:
                presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_OK, result);
                break;
            default:
                presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationError(Throwable cause) {
        PaymentResult result;

        if (cause instanceof PaymentException) {
            PaymentException pe = (PaymentException) cause;
            result = new PaymentResult(pe.getMessage(), pe.error);
        } else {
            String resultInfo = cause.toString();
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, resultInfo);
            result = new PaymentResult(resultInfo, error);
        }
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
    }
}
