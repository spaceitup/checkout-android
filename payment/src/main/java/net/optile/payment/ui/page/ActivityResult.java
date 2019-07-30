/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import net.optile.payment.ui.PaymentResult;

/**
 * Class holding the onActivityResult payment result
 */
public class ActivityResult {

    final int requestCode;
    final int resultCode;
    final PaymentResult paymentResult;

    /**
     * Construct a new ActivityResult Object
     *
     * @param requestCode activity requestCode
     * @param resultCode activity resultCode
     * @param paymentResult containing the result of the payment request
     */
    public ActivityResult(int requestCode, int resultCode, PaymentResult paymentResult) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.paymentResult = paymentResult;
    }
}
