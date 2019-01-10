/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example.checkout;

import net.optile.payment.ui.PaymentResult;

/**
 * Class for holding the CheckoutResult data
 */
class CheckoutResult {

    /** The PaymentPage resultCode */
    public int resultCode;

    /** The PaymentResult received from the Payment SDK, this may be null */
    public PaymentResult paymentResult;

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
}
