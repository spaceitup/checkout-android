/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import static net.optile.payment.model.InteractionCode.PROCEED;
import static net.optile.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static net.optile.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import android.app.Activity;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.OperationType;
import net.optile.payment.model.Redirect;
import net.optile.payment.model.RedirectType;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.redirect.RedirectRequest;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.OperationListener;
import net.optile.payment.ui.service.OperationService;
import net.optile.payment.util.PaymentResultHelper;

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
                case OperationType.PRESET:
                case OperationType.UPDATE:
                case OperationType.ACTIVATION:
                    return InteractionCode.ABORT;
            }
        }
        return InteractionCode.VERIFY;
    }
}
