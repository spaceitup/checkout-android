/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import static net.optile.payment.model.InteractionReason.CLIENTSIDE_ERROR;
import static net.optile.payment.model.InteractionReason.COMMUNICATION_FAILURE;

import android.app.Activity;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.InteractionReason;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;
import net.optile.payment.model.RedirectType;
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
    private Operation operation;
    
    /**
     * Create a new BasicNetworkService, this service is a basic implementation
     * that simply send an operation to the Payment API.
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
    public void preparePayment(Activity activity, int requestCode, Operation operation) throws PaymentException {
        PaymentResult result = new PaymentResult("preparePayment not required");
        presenter.onPreparePaymentResult(PaymentUI.RESULT_CODE_OK, result);
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
        String code = interaction.getCode();
        int resultCode = InteractionCode.PROCEED.equals(code) ? PaymentUI.RESULT_CODE_OK :
            PaymentUI.RESULT_CODE_CANCELED;

        PaymentResult result = new PaymentResult(operationResult);
        presenter.onProcessPaymentResult(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectCanceled() {
        String code = getErrorInteractionCode(operation);
        Interaction interaction = new Interaction(code, COMMUNICATION_FAILURE);
        String resultInfo = "Missing OperationResult after client-side redirect";

        PaymentResult result = new PaymentResult(resultInfo, interaction);
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationSuccess(OperationResult operationResult) {
        Interaction interaction = operationResult.getInteraction();
        PaymentResult result = new PaymentResult(operationResult);
        
        if (!InteractionCode.PROCEED.equals(interaction.getCode())) {
            presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
            return;
        }
        if (operationResult.getRedirect() != null) {
            handleRedirect(operationResult);
            return;
        }
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_OK, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationError(Throwable cause) {
        PaymentError error = PaymentError.fromThrowable(cause);
        handleProcessPaymentError(error);
    }

    private void handleRedirect(OperationResult operationResult) {
        Redirect redirect = operationResult.getRedirect();
        switch (redirect.getType()) {
            case RedirectType.PROVIDER:
            case RedirectType.HANDLER3DS2:
                try {
                    presenter.redirectPayment(redirect);
                } catch (PaymentException e) {
                    handleProcessPaymentError(e.error);
                }
                break;
            default:
                PaymentResult result = new PaymentResult(operationResult);
                presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_OK, result);
        }
    }

    private void handleProcessPaymentError(PaymentError error) {
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, toPaymentResult(error));
    }

    private PaymentResult toPaymentResult(PaymentError error) {
        ErrorInfo errorInfo = error.getErrorInfo();
        if (errorInfo != null) {
            return new PaymentResult(errorInfo.getInteraction(), error);
        }
        String reason = error.isNetworkFailure() ? InteractionReason.COMMUNICATION_FAILURE : InteractionReason.CLIENTSIDE_ERROR;
        Interaction interaction = new Interaction(getErrorInteractionCode(operation), reason); 
        return new PaymentResult(interaction, error);
    }

    private String getErrorInteractionCode(Operation operation) {
        if (operation != null) {
            switch (operation.getType()) {
                case Operation.PRESET:
                case Operation.UPDATE:
                case Operation.ACTIVATE:
                    return InteractionCode.ABORT;
            }
        }
        return InteractionCode.VERIFY;
    }
}
