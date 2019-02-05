/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentMethodCodesTest {

    @Test
    public void isPaymentMethodCode_invalidValue_false() {
        assertFalse(PaymentMethodCodes.isValid("foo"));
    }

    @Test
    public void isPaymentMethodCode_validValue_true() {
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.AMEX));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.CASTORAMA));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.DINERS));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.DISCOVER));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.MASTERCARD));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.UNIONPAY));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.VISA));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.VISA_DANKORT));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.VISAELECTRON));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.CARTEBANCAIRE));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.MAESTRO));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.MAESTROUK));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.POSTEPAY));
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.SOLO));
    }
}
