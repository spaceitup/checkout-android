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

package net.optile.payment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static net.optile.payment.model.PaymentMethod.isPaymentMethod;

/**
 * The PaymentMethod test
 */
public class PaymentMethodTest {

    @Test
    public void isPaymentMethod_invalidValue_false() {
        assertFalse(PaymentMethod.isPaymentMethod("foo"));
    }

    @Test
    public void isPaymentMethod_validValue_true() {
        assertTrue(isPaymentMethod(PaymentMethod.BANK_TRANSFER));
        assertTrue(isPaymentMethod(PaymentMethod.BILLING_PROVIDER));
        assertTrue(isPaymentMethod(PaymentMethod.CASH_ON_DELIVERY));
        assertTrue(isPaymentMethod(PaymentMethod.CHECK_PAYMENT));
        assertTrue(isPaymentMethod(PaymentMethod.CREDIT_CARD));
        assertTrue(isPaymentMethod(PaymentMethod.DEBIT_CARD));
        assertTrue(isPaymentMethod(PaymentMethod.DIRECT_DEBIT));
        assertTrue(isPaymentMethod(PaymentMethod.ELECTRONIC_INVOICE));
        assertTrue(isPaymentMethod(PaymentMethod.GIFT_CARD));
        assertTrue(isPaymentMethod(PaymentMethod.MOBILE_PAYMENT));
        assertTrue(isPaymentMethod(PaymentMethod.ONLINE_BANK_TRANSFER));
        assertTrue(isPaymentMethod(PaymentMethod.OPEN_INVOICE));
        assertTrue(isPaymentMethod(PaymentMethod.PREPAID_CARD));
        assertTrue(isPaymentMethod(PaymentMethod.TERMINAL));
        assertTrue(isPaymentMethod(PaymentMethod.WALLET));
    }
}
