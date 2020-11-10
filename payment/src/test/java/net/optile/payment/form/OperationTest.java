/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.form;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

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
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", createTestURL());
        operation.putValue(null, "Foo");
    }

    @Test
    public void putValue_success() throws PaymentException, JSONException {
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", createTestURL());
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

    private URL createTestURL() {
        URL url = null;

        try {
            url = new URL("http://localhost/charge");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        return url;
    }
}

