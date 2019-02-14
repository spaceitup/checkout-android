/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.checkout;

import android.util.Log;
import net.optile.payment.ui.PaymentUI;

/**
 * CheckoutPresenter responsible for communicating with the Merchant Backend to obtain a new list URL.
 * Once a new list URL has been loaded from the Merchant backend the optile PaymentPage can be opened.
 */
final class CheckoutPresenter {

    private CheckoutView view;

    /**
     * Construct a new CheckoutPresenter
     *
     * @param view the view
     */
    CheckoutPresenter(final CheckoutView view) {
        this.view = view;
    }

    /**
     * Handle the received checkout result from the optile Payment SDK.
     *
     * @param result the result received from the SDK
     */
    void handleCheckoutResult(CheckoutResult result) {
        String tag = "checkout";

        switch (result.resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                // Payment Operation request has been completed, result.paymentResult contains an Interaction and optional OperationResult.
                view.showPaymentSuccess();
                Log.i(tag, "OK[" + result.paymentResult + "]");
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                // Payment has been canceled, the result.paymentResult may be null if the user closed the Payment Page without making any requests.
                // If result.paymentResult is not null then it contains an Interaction and optional OperationResult. 
                if (result.paymentResult != null) {
                    Log.i(tag, "Canceled[" + result.paymentResult + "]");
                } else {
                    Log.i(tag, "Canceled[unknown]");
                }
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                // An internal error occurred and the result.paymentResult contains a PaymentError explaining the reason of the error.
                // I.e. a Connection, Protocol or Security error can be returned as a PaymentError. 
                Log.i(tag, "Error[" + result.paymentResult + "]");
        }
    }

    /**
     * Load here the list URL from the Merchant backend and once loaded, instruct the CheckoutView to
     * open the PaymentPage using the PaymentUI class.
     */
    void startPayment() {
        String listUrl = ""; // load the list url here
        view.openPaymentPage(listUrl);
    }
}
