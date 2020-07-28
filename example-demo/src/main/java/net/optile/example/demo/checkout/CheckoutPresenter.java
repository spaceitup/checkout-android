/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

import static net.optile.payment.model.InteractionReason.PRESETACCOUNT_SELECTED;
import static net.optile.payment.model.RedirectType.SUMMARY;

import java.util.Objects;

import net.optile.example.demo.shared.SdkResult;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.OperationType;
import net.optile.payment.model.Redirect;
import net.optile.payment.model.RedirectType;
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
     * @param result the result received from the SDK
     */
    void handleSdkResult(SdkResult result) {
        switch (result.resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                handleResultOk(result.paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handleResultCanceled(result.paymentResult);
                break;
        }
    }

    private void handleResultOk(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        if (interaction == null) {
            return;
        }
        if (Objects.equals(PRESETACCOUNT_SELECTED, interaction.getReason())) {
            view.showPaymentSummary();
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
        view.showPaymentConfirmed();
    }


    private void handleResultCanceled(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.ABORT:
                if (!result.hasNetworkFailureError()) {
                    view.closePayment();
                }
                break;
            case InteractionCode.VERIFY:
                // VERIFY means that a charge request has been made but the status of the payment could
                // not be verified by the Android-SDK, i.e. because of a network error
                view.closePayment();
        }
    }
}
