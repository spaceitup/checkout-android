/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.shared;

import net.optile.payment.ui.PaymentResult;

/**
 * Class for holding the result data obtained from the Android SDK
 */
public class SdkResult {

    public final int requestCode;
    public final int resultCode;
    public final PaymentResult paymentResult;

    /**
     * Construct a new SdkResult
     *
     * @param requestCode the code identifying the request type
     * @param resultCode indicating the payment resultCode
     * @param paymentResult result containing PaymentResult details
     */
    public SdkResult(int requestCode, int resultCode, PaymentResult paymentResult) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.paymentResult = paymentResult;
    }
}
