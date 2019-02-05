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

public class PaymentInputTypeTest {

    @Test
    public void isPaymentInputType_invalidValue_false() {
        assertFalse(PaymentInputType.isValid("foo"));
    }

    @Test
    public void isPaymentInputType_validValue_true() {
        assertTrue(PaymentInputType.isValid(PaymentInputType.ACCOUNT_NUMBER));
        assertTrue(PaymentInputType.isValid(PaymentInputType.HOLDER_NAME));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_DATE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_MONTH));
        assertTrue(PaymentInputType.isValid(PaymentInputType.EXPIRY_YEAR));
        assertTrue(PaymentInputType.isValid(PaymentInputType.VERIFICATION_CODE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BANK_CODE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.IBAN));
        assertTrue(PaymentInputType.isValid(PaymentInputType.BIC));
        assertTrue(PaymentInputType.isValid(PaymentInputType.ALLOW_RECURRENCE));
        assertTrue(PaymentInputType.isValid(PaymentInputType.AUTO_REGISTRATION));
    }
}
