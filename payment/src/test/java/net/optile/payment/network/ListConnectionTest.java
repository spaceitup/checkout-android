/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.network;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * The type List connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ListConnectionTest {

    /**
     * Create payment session invalid authorization invalid value error.
     */
    @Test
    public void createPaymentSession_invalidAuthorization_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.createPaymentSession(null, "{}");
        assertTrue(resp.hasError(NetworkError.INVALID_VALUE));
    }

    /**
     * Create payment session invalid list data invalid value error.
     */
    @Test
    public void createPaymentSession_invalidListData_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.createPaymentSession("abc123", "");
        assertTrue(resp.hasError(NetworkError.INVALID_VALUE));
    }

    /**
     * Gets list result invalid url invalid value error.
     */
    @Test
    public void getListResult_invalidURL_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.getListResult(null);
        assertTrue(resp.hasError(NetworkError.INVALID_VALUE));
    }
}
