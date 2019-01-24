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

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;

@RunWith(RobolectricTestRunner.class)
public class OperationTest {

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
        Operation operation = new Operation();
        operation.putValue(null, "Foo");
    }

    @Test
    public void putValue_success() throws PaymentException, JSONException {
        Operation operation = new Operation();
        operation.putValue(PaymentInputType.ACCOUNT_NUMBER, "accountnumber123");
        operation.putValue(PaymentInputType.HOLDER_NAME, "John Doe");
        operation.putValue(PaymentInputType.EXPIRY_MONTH, 12);
        operation.putValue(PaymentInputType.EXPIRY_YEAR, 2019);
        operation.putValue(PaymentInputType.VERIFICATION_CODE, "123");
        operation.putValue(PaymentInputType.BANK_CODE, "bankcode123");
        operation.putValue(PaymentInputType.IBAN, "iban123");
        operation.putValue(PaymentInputType.BIC, "bic123");
        operation.putValue(PaymentInputType.ALLOW_RECURRENCE, "true");
        operation.putValue(PaymentInputType.AUTO_REGISTRATION, "true");
        expect(operation.toJson()).toMatchSnapshot();
    }
}

