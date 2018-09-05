/**
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
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class ListConnectionTest {

    @Test
    public void createPaymentSession_invalidAuthorization_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.createPaymentSession(null, "{}");
        assertTrue(resp.hasError(NetworkError.ErrorType.INVALID_VALUE));
    }

    @Test
    public void createPaymentSession_invalidListData_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.createPaymentSession("abc123", "");
        assertTrue(resp.hasError(NetworkError.ErrorType.INVALID_VALUE));
    }

    @Test    
    public void getListResult_invalidURL_invalidValueError() {

        ListConnection conn = new ListConnection("http://localhost");
        NetworkResponse resp = conn.getListResult(null);
        assertTrue(resp.hasError(NetworkError.ErrorType.INVALID_VALUE));
    }
}
