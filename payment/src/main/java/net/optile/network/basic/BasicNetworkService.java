/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import android.app.Activity;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
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
        presenter.showProgress(true);
        service.postOperation(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectSuccess(OperationResult result) {
        Interaction interaction = result.getInteraction();
        String code = interaction.getCode();
        int resultCode = InteractionCode.PROCEED.equals(code) ? PaymentUI.RESULT_CODE_OK : PaymentUI.RESULT_CODE_CANCELED;        
        presenter.onProcessPaymentResult(resultCode, new PaymentResult(result));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onRedirectCanceled() {
        Interaction interaction = new Interaction(InteractionCode.VERIFY, InteractionReason.COMMUNICATION_FAILURE);
        String resultInfo = "Missing OperationResult after client-side redirect";
        PaymentResult result = new PaymentResult(resultInfo, interaction);        
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationSuccess(OperationResult result) {
        Interaction interaction = result.getInteraction();
        String code = interaction.getCode();
        if (!InteractionCode.PROCEED.equals(code)) {
            presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_CANCELED, new PaymentResult(result));
            return;
        }
        Redirect redirect = result.getRedirect();
        if (redirect != null) {
            switch (redirect.getType()) {
                case RedirectType.PROVIDER:
                case RedirectType.HANDLER3DS2:
                    redirectPayment(redirect);
                    return;
            }
        }
        presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_OK, new PaymentResult(result));        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOperationError(Throwable cause) {
        PaymentResult result = PaymentResult.fromThrowable(cause);
        int status = result.hasError() ? PaymentUI.RESULT_CODE_ERROR : PaymentUI.RESULT_CODE_CANCELED;
        presenter.onPreparePaymentResult(status, result);
    }

    private void redirectPayment(Redirect redirect) {
        try {
            presenter.redirectPayment(redirect);
        } catch (PaymentException e) {
            PaymentResult result = PaymentResult.fromPaymentException(e);
            presenter.onProcessPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);        
        }
    }
}
