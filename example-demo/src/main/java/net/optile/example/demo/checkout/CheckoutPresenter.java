/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

import static net.optile.payment.model.RedirectType.SUMMARY;

import java.util.Objects;

import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;
import net.optile.payment.ui.PaymentActivityResult;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;

/**
 * CheckoutPresenter takes care of handling the response from the Android SDK payment page.
 */
final class CheckoutPresenter {

    private final CheckoutView view;

    /**
     * Construct a new CheckoutPresenter
     *
     * @param view the checkout view
     */
    CheckoutPresenter(CheckoutView view) {
        this.view = view;
    }

    /**
     * Handle the received checkout result from the Android SDK.
     *
     * @param sdkResult the result received from the Android SDK
     */
    void handleSdkResult(PaymentActivityResult sdkResult) {
        PaymentResult paymentResult = sdkResult.getPaymentResult();
        switch (sdkResult.getResultCode()) {
            case PaymentUI.RESULT_CODE_PROCEED:
                handlePaymentResultProceed(paymentResult);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handlePaymentResultError(paymentResult);
                break;
        }
    }

    private void handlePaymentResultProceed(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        if (interaction == null) {
            return;
        }
        OperationResult op = result.getOperationResult();
        if (op != null) {
            Redirect redirect = op.getRedirect();
            if (redirect != null && Objects.equals(SUMMARY, redirect.getType())) {
                view.showPaymentSummary();
                return;
            }
        }
        view.showPaymentConfirmation();
    }

    private void handlePaymentResultError(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.ABORT:
                if (!result.isNetworkFailure()) {
                    view.stopPaymentWithErrorMessage();
                }
                break;
            case InteractionCode.VERIFY:
                // VERIFY means that a charge request has been made but the status of the payment could
                // not be verified by the Android-SDK, i.e. because of a network error
                view.stopPaymentWithErrorMessage();
        }
    }
}
