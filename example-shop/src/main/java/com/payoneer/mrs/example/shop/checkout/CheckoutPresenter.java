/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.example.shop.checkout;

import static com.payoneer.mrs.payment.model.RedirectType.SUMMARY;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import java.util.Objects;

import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.model.Redirect;
import com.payoneer.mrs.payment.ui.PaymentActivityResult;
import com.payoneer.mrs.payment.ui.PaymentResult;

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
            case RESULT_CODE_PROCEED:
                handlePaymentResultProceed(paymentResult);
                break;
            case RESULT_CODE_ERROR:
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
