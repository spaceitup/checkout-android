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

import net.optile.payment.form.Charge;
import net.optile.payment.model.OperationResult;

/**
 * The type Charge connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ChargeConnectionTest {

    /**
     * Create charge.
     *
     * @throws NetworkException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createCharge_invalidURL_exception() throws NetworkException {
        ChargeConnection conn = new ChargeConnection();
        Charge charge = new Charge();
        OperationResult result = conn.createCharge(null, charge);
    }

    /**
     * Create charge invalid data invalid value error.
     *
     * @throws NetworkException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createCharge_invalidData_exception() throws NetworkException {
        ChargeConnection conn = new ChargeConnection();
        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        OperationResult result = conn.createCharge(url, null);
    }
}
