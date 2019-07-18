/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(PaymentMethodCodes.isValid(PaymentMethodCodes.SEPADD));
    }
}
