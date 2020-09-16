/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui;

/**
 * A container for a payment activity result as obtained from the Android SDK
 */
public final class PaymentActivityResult {

    private final int requestCode;
    private final int resultCode;
    private final PaymentResult paymentResult;

    /**
     * Construct a new ActivityResult Object
     *
     * @param requestCode activity requestCode
     * @param resultCode activity resultCode
     * @param paymentResult containing the result of the payment request
     */
    public PaymentActivityResult(int requestCode, int resultCode, PaymentResult paymentResult) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.paymentResult = paymentResult;
    }

    /**
     * Get a string representation of the resultCode
     *
     * @return the String representation of the resultCode
     */
    public static String resultCodeToString(int resultCode) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_PROCEED:
                return "RESULT_CODE_PROCEED";
            case PaymentUI.RESULT_CODE_ERROR:
                return "RESULT_CODE_ERROR";
            default:
                return "Unknown";
        }
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public PaymentResult getPaymentResult() {
        return paymentResult;
    }
}
