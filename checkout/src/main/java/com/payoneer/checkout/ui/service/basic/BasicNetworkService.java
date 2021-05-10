/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service.basic;

import static com.payoneer.checkout.model.InteractionCode.PROCEED;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.NetworkOperationType;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.model.Redirect;
import com.payoneer.checkout.model.RedirectType;
import com.payoneer.checkout.redirect.RedirectRequest;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.service.NetworkService;
import com.payoneer.checkout.ui.service.OperationListener;
import com.payoneer.checkout.ui.service.OperationService;
import com.payoneer.checkout.util.PaymentResultHelper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

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
    public void processPayment(Activity activity, int requestCode, Operation operation) {
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
