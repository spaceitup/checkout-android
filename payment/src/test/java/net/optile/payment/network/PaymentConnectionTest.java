/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;

/**
 * The type Operation connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class PaymentConnectionTest {

    /**
     * Post operation invalid data invalid value error.
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createOperation_invalidData_exception() throws PaymentException {
        PaymentConnection conn = new PaymentConnection();
        conn.postOperation(null);
    }
}
