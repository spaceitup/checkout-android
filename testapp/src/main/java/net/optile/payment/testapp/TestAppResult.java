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

package net.optile.payment.testapp;

import net.optile.payment.ui.PaymentResult;

/**
 * Class for holding the TestAppResult data
 */
class TestAppResult {

    /** Indicating if the checkout was successful */
    public boolean success;

    /** The PaymentResult received from the Payment SDK, this may be null */
    public PaymentResult paymentResult;

    /**
     * Construct a new TestAppResult
     *
     * @param success indicating the payment was successful or failed
     * @param paymentResult optional result containing PaymentResult details
     */
    public TestAppResult(boolean success, PaymentResult paymentResult) {
        this.success = success;
        this.paymentResult = paymentResult;
    }
}
