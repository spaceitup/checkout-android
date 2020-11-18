/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui;

import android.app.Activity;
import android.content.Intent;
import net.optile.payment.util.PaymentResultHelper;

/**
 * A container for a payment activity result as obtained from the Android SDK
 */
public final class PaymentActivityResult {

    public final static int RESULT_CODE_PROCEED = Activity.RESULT_FIRST_USER;
    public final static int RESULT_CODE_ERROR = Activity.RESULT_FIRST_USER + 1;

    private final int requestCode;
    private final int resultCode;
    private final PaymentResult paymentResult;

    /**
     * Construct a new PaymentActivityResult Object
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
     * Construct a new PaymentActivityResult Object
     *
     * @param requestCode activity requestCode
     * @param resultCode activity resultCode
     * @param data containing the activity result data
     */
    public static PaymentActivityResult fromActivityResult(int requestCode, int resultCode, Intent data) {
        PaymentResult result = PaymentResultHelper.fromResultIntent(data);
        return new PaymentActivityResult(requestCode, resultCode, result);
    }

    /**
     * Get a string representation of the resultCode
     *
     * @return the String representation of the resultCode
     */
    public static String resultCodeToString(int resultCode) {
        switch (resultCode) {
            case RESULT_CODE_PROCEED:
                return "RESULT_CODE_PROCEED";
            case RESULT_CODE_ERROR:
                return "RESULT_CODE_ERROR";
            case Activity.RESULT_CANCELED:
                return "Activity.RESULT_CANCELED";
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
