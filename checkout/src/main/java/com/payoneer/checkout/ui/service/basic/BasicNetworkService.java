/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service.basic;

import static com.payoneer.checkout.model.InteractionCode.ABORT;
import static com.payoneer.checkout.model.InteractionCode.PROCEED;
import static com.payoneer.checkout.model.InteractionCode.VERIFY;
import static com.payoneer.checkout.model.NetworkOperationType.ACTIVATION;
import static com.payoneer.checkout.model.NetworkOperationType.PRESET;
import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.DeleteAccount;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.model.Redirect;
import com.payoneer.checkout.model.RedirectType;
import com.payoneer.checkout.redirect.RedirectRequest;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.service.NetworkService;
import com.payoneer.checkout.ui.service.OperationListener;
import com.payoneer.checkout.ui.service.OperationService;
import com.payoneer.checkout.util.PaymentResultHelper;

import android.content.Context;

/**
 * BasicNetworkService implementing the handling of basic payment methods like Visa, Mastercard and Sepa.
 * This network service also supports redirect networks like Paypal.
 */
public final class BasicNetworkService extends NetworkService implements OperationListener {

    private final OperationService operationService;
    private Operation operation;
    
    /**
     * Create a new BasicNetworkService, this service is a basic implementation
     * that sends an operation to the Payment API.
     *
     * @param context context in which this network service will operate
     */
    public BasicNetworkService(Context context) {
        operationService = new OperationService(context);
        operationService.setListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        operationService.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processPayment(Operation operation) {
        if (operationService.isActive()) {
            throw new IllegalStateException("BasicNetworkService is active, stop first");
        }
        this.operation = operation;
        listener.showProgress(true);
        operationService.postOperation(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAccount(DeleteAccount account) {
        if (operationService.isActive()) {
            throw new IllegalStateException("BasicNetworkService is active, stop first");
        }
        listener.showProgress(true);
        operationService.deleteAccount(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectResult(OperationResult operationResult) {
        int resultCode;
        PaymentResult paymentResult;
        
        if (operationResult != null) {
            Interaction interaction = operationResult.getInteraction();
            resultCode = PROCEED.equals(interaction.getCode()) ? RESULT_CODE_PROCEED : RESULT_CODE_ERROR;
            paymentResult = new PaymentResult(operationResult);
        } else {
            String message = "Missing OperationResult after client-side redirect";
            resultCode = RESULT_CODE_ERROR;
            paymentResult = PaymentResultHelper.fromErrorMessage(VERIFY, message);
        }
        listener.onProcessPaymentResult(resultCode, paymentResult);

    }

    private void handleRedirect(OperationResult operationResult) {
        Redirect redirect = operationResult.getRedirect();
        switch (redirect.getType()) {
            case RedirectType.PROVIDER:
            case RedirectType.HANDLER3DS2:
                try {
                    RedirectRequest request = RedirectRequest.fromOperationResult(operationResult);
                    listener.redirect(request);
                } catch (PaymentException e) {
                    onOperationError(e);
                }
                break;
            default:
                PaymentResult result = new PaymentResult(operationResult);
                listener.onProcessPaymentResult(RESULT_CODE_PROCEED, result);
        }
    }

    @Override
    public void onDeleteAccountSuccess(OperationResult operationResult) {
        Interaction interaction = operationResult.getInteraction();
        PaymentResult result = new PaymentResult(operationResult);
        int resultCode = PROCEED.equals(interaction.getCode()) ? RESULT_CODE_PROCEED : RESULT_CODE_ERROR;
        listener.onDeleteAccountResult(resultCode, result);
    }

    @Override
    public void onDeleteAccountError(Throwable cause) {
        PaymentResult paymentResult = PaymentResultHelper.fromThrowable(ABORT, cause);
        listener.onDeleteAccountResult(RESULT_CODE_ERROR, paymentResult);
    }

    @Override
    public void onOperationSuccess(OperationResult operationResult) {
        Interaction interaction = operationResult.getInteraction();
        PaymentResult result = new PaymentResult(operationResult);

        if (!PROCEED.equals(interaction.getCode())) {
            listener.onProcessPaymentResult(RESULT_CODE_ERROR, result);
            return;
        }
        if (operationResult.getRedirect() != null) {
            handleRedirect(operationResult);
            return;
        }
        listener.onProcessPaymentResult(RESULT_CODE_PROCEED, result);
    }

    @Override
    public void onOperationError(Throwable cause) {
        String code = getErrorInteractionCode(this.operation);
        PaymentResult paymentResult = PaymentResultHelper.fromThrowable(code, cause);
        listener.onProcessPaymentResult(RESULT_CODE_ERROR, paymentResult);
    }

    private String getErrorInteractionCode(Operation operation) {
        if (operation != null) {
            switch (operation.getOperationType()) {
                case PRESET:
                case UPDATE:
                case ACTIVATION:
                    return ABORT;
            }
        }
        return VERIFY;
    }
}
