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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.model.ListResult;

/**
 * The type List connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ListConnectionTest {

    /**
     * Create payment session invalid authorization invalid value error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidAuthorization_invalidValueError() throws NetworkException {
        ListConnection conn = new ListConnection("http://localhost");
        ListResult result = conn.createPaymentSession(null, "{}");
    }

    /**
     * Create payment session invalid list data invalid value error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidListData_invalidValueError() throws NetworkException {
        ListConnection conn = new ListConnection("http://localhost");
        ListResult result = conn.createPaymentSession("abc123", "");
    }

    /**
     * Gets list result invalid url invalid value error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getListResult_invalidURL_invalidValueError() throws NetworkException {
        ListConnection conn = new ListConnection("http://localhost");
        ListResult result = conn.getListResult(null);
    }
}
