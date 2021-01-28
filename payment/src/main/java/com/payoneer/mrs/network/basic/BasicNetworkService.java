/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.network.basic;

import static com.payoneer.mrs.payment.model.InteractionCode.PROCEED;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.form.Operation;
import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.NetworkOperationType;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.model.Redirect;
import com.payoneer.mrs.payment.model.RedirectType;
import com.payoneer.mrs.payment.ui.PaymentResult;
import com.payoneer.mrs.payment.ui.redirect.RedirectRequest;
import com.payoneer.mrs.payment.ui.service.NetworkService;
import com.payoneer.mrs.payment.ui.service.OperationListener;
import com.payoneer.mrs.payment.ui.service.OperationService;
import com.payoneer.mrs.payment.util.PaymentResultHelper;

import android.app.Activity;
import android.content.Context;

/**
 * BasicNetworkService implementing the handling of basic payment methods like Visa, Mastercard and Sepa.
 * This network service also supports redirect networks like Paypal.
 */
public final class BasicNetworkService extends NetworkService implements OperationListener {
    private final OperationService service;
    private Operation operation;

    /**
     * Create a new BasicNetworkService, this service is a basic implementation
     * that sends an operation to the Payment API.
     *
     * @param context context in which this network service will operate
     */
    public BasicNetworkService(Context context) {
        service = new OperationService(context);
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
    public void processPayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
        this.operation = operation;
        presenter.showProgress(true);
        service.postOperation(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectSuccess(OperationResult operationResult) {
        Interaction interaction = operationResult.getInteraction();
        int resultCode = PROCEED.equals(interaction.getCode()) ? RESULT_CODE_PROCEED : RESULT_CODE_ERROR;
        PaymentResult result = new PaymentResult(operationResult);
        presenter.onProcessPaymentResult(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectError() {
        String code = getErrorInteractionCode(operation);
        String message = "Missing OperationResult after client-side redirect";
        PaymentResult paymentResult = PaymentResultHelper.fromErrorMessage(code, message);
        presenter.onProcessPaymentResult(RESULT_CODE_ERROR, paymentResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationSuccess(OperationResult operationResult) {
        Interaction interaction = operationResult.getInteraction();
        PaymentResult result = new PaymentResult(operationResult);

        if (!PROCEED.equals(interaction.getCode())) {
            presenter.onProcessPaymentResult(RESULT_CODE_ERROR, result);
            return;
        }
        if (operationResult.getRedirect() != null) {
            handleRedirect(operationResult);
            return;
        }
        presenter.onProcessPaymentResult(RESULT_CODE_PROCEED, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationError(Throwable cause) {
        handleProcessPaymentError(cause);
    }

    private void handleRedirect(OperationResult operationResult) {
        Redirect redirect = operationResult.getRedirect();
        switch (redirect.getType()) {
            case RedirectType.PROVIDER:
            case RedirectType.HANDLER3DS2:
                try {
                    RedirectRequest request = RedirectRequest.fromOperationResult(operationResult);
                    presenter.redirect(request);
                } catch (PaymentException e) {
                    handleProcessPaymentError(e);
                }
                break;
            default:
                PaymentResult result = new PaymentResult(operationResult);
                presenter.onProcessPaymentResult(RESULT_CODE_PROCEED, result);
        }
    }

    private void handleProcessPaymentError(Throwable cause) {
        String code = getErrorInteractionCode(this.operation);
        PaymentResult paymentResult = PaymentResultHelper.fromThrowable(code, cause);
        presenter.onProcessPaymentResult(RESULT_CODE_ERROR, paymentResult);
    }

    private String getErrorInteractionCode(Operation operation) {
        if (operation != null) {
            switch (operation.getOperationType()) {
                case NetworkOperationType.PRESET:
                case NetworkOperationType.UPDATE:
                case NetworkOperationType.ACTIVATION:
                    return InteractionCode.ABORT;
            }
        }
        return InteractionCode.VERIFY;
    }
}
