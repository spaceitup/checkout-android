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

package net.optile.payment.network;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;

/**
 * The type Operation connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class OperationConnectionTest {

    /**
     * Create charge.
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createCharge_invalidURL_exception() throws PaymentException {
        OperationConnection conn = new OperationConnection();
        Operation operation = new Operation();
        conn.createCharge(null, operation);
    }

    /**
     * Create charge invalid data invalid value error.
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createCharge_invalidData_exception() throws PaymentException {
        OperationConnection conn = new OperationConnection();
        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        conn.createCharge(url, null);
    }
}
