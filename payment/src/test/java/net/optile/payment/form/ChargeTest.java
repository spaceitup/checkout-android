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

package net.optile.payment.form;

import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

@RunWith(RobolectricTestRunner.class)
public class ChargeTest {

    @BeforeClass
    public static void beforeAll() {
        start();
    }

    @AfterClass
    public static void afterAll() {
        validateSnapshots();
    }

    @Test(expected = IllegalArgumentException.class)
    public void putValue_invalidName_exception() throws PaymentException {
        Charge charge = new Charge();
        charge.putValue(null, "Foo");
    }

    @Test
    public void putValue_success() throws PaymentException, JSONException {
        Charge charge = new Charge();
        charge.putValue(PaymentInputType.ACCOUNT_NUMBER, "accountnumber123");
        charge.putValue(PaymentInputType.HOLDER_NAME, "John Doe");
        charge.putValue(PaymentInputType.EXPIRY_MONTH, 12);
        charge.putValue(PaymentInputType.EXPIRY_YEAR, 2019);
        charge.putValue(PaymentInputType.VERIFICATION_CODE, "123");
        charge.putValue(PaymentInputType.BANK_CODE, "bankcode123");
        charge.putValue(PaymentInputType.IBAN, "iban123");
        charge.putValue(PaymentInputType.BIC, "bic123");
        charge.putValue(PaymentInputType.ALLOW_RECURRENCE, "true");
        charge.putValue(PaymentInputType.AUTO_REGISTRATION, "true");
        expect(charge.toJson()).toMatchSnapshot();
    }
}

