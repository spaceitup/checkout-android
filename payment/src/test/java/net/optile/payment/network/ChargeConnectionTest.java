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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * The type Charge connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ChargeConnectionTest {

    /**
     * Create charge.
     *
     * @throws Exception the exception
     */
    @Test
    public void createCharge() throws Exception {

        ChargeConnection conn = new ChargeConnection();
        NetworkResponse resp = conn.createCharge(null, "{}");
        assertTrue(resp.hasError(NetworkError.INVALID_VALUE));
    }

    /**
     * Create charge invalid data invalid value error.
     */
    @Test
    public void createCharge_invalidData_invalidValueError() {

        ChargeConnection conn = new ChargeConnection();

        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
        }
        assertNotNull(url);

        NetworkResponse resp = conn.createCharge(url, "");
        assertTrue(resp.hasError(NetworkError.INVALID_VALUE));
    }
}
