/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.checkout;

import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;

/**
 * Class for holding the CheckoutResult data
 */
class CheckoutResult {

    /** The PaymentPage resultCode */
    public final int resultCode;

    /** The PaymentResult received from the Payment SDK, this may be null */
    public final PaymentResult paymentResult;

    /**
     * Construct a new TestAppResult
     *
     * @param resultCode indicating the payment resultCode
     * @param paymentResult optional result containing PaymentResult details
     */
    public CheckoutResult(int resultCode, PaymentResult paymentResult) {
        this.resultCode = resultCode;
        this.paymentResult = paymentResult;
    }

    /**
     * Get a string representation of the resultCode
     *
     * @return the String representation of the resultCode
     */
    public String getResultCodeString() {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                return "RESULT_CODE_OK";
            case PaymentUI.RESULT_CODE_CANCELED:
                return "RESULT_CODE_CANCELED";
            case PaymentUI.RESULT_CODE_ERROR:
                return "RESULT_CODE_ERROR";
            default:
                return "Unknown";
        }
    }
}
