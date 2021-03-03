/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.form;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.core.PaymentInputType;
import com.payoneer.mrs.payment.model.BrowserData;
import com.payoneer.mrs.test.util.TestUtils;

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
        URL url = TestUtils.createTestURL("http://localhost/charge");
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
        operation.putStringValue(null, "Foo");
    }

    @Test
    public void putValue_success() throws PaymentException {
        URL url = TestUtils.createTestURL("http://localhost/charge");
        Operation operation = new Operation("VISA", "CREDIT_CARD", "CHARGE", url);
        operation.putStringValue(PaymentInputType.HOLDER_NAME, "John Doe");
        operation.putStringValue(PaymentInputType.ACCOUNT_NUMBER, "accountnumber123");
        operation.putStringValue(PaymentInputType.BANK_CODE, "bankcode123");
        operation.putStringValue(PaymentInputType.BANK_NAME, "bankname123");
        operation.putStringValue(PaymentInputType.BIC, "bic123");
        operation.putStringValue(PaymentInputType.BRANCH, "branch123");
        operation.putStringValue(PaymentInputType.CITY, "city123");
        operation.putStringValue(PaymentInputType.EXPIRY_MONTH, "12");
        operation.putStringValue(PaymentInputType.EXPIRY_YEAR, "2019");
        operation.putStringValue(PaymentInputType.IBAN, "iban123");
        operation.putStringValue(PaymentInputType.LOGIN, "login123");
        operation.putBooleanValue(PaymentInputType.OPTIN, true);
        operation.putStringValue(PaymentInputType.PASSWORD, "password123");
        operation.putStringValue(PaymentInputType.VERIFICATION_CODE, "123");
        operation.putStringValue(PaymentInputType.CUSTOMER_BIRTHDAY, "3");
        operation.putStringValue(PaymentInputType.CUSTOMER_BIRTHMONTH, "12");
        operation.putStringValue(PaymentInputType.CUSTOMER_BIRTHYEAR, "72");
        operation.putStringValue(PaymentInputType.INSTALLMENT_PLANID, "72");
        operation.putBooleanValue(PaymentInputType.ALLOW_RECURRENCE, true);
        operation.putBooleanValue(PaymentInputType.AUTO_REGISTRATION, true);

        BrowserData browserData = new BrowserData();
        browserData.setJavaEnabled(true);
        browserData.setLanguage("en");
        browserData.setTimezone("Berlin/Europe");
        browserData.setColorDepth(24);
        browserData.setBrowserScreenWidth(680);
        browserData.setBrowserScreenHeight(760);
        operation.setBrowserData(browserData);

        expect(operation.toJson()).toMatchSnapshot();
    }
}

