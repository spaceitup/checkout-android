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
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;

/**
 * The type List connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class ListConnectionTest {

    /**
     * Create payment session invalid baseUrl
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidBaseUrl_IllegalArgumentException() throws PaymentException {
        ListConnection conn = new ListConnection();
        ListResult result = conn.createPaymentSession(null, "auth123", "{}");
    }

    /**
     * Create payment session invalid authorization
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidAuthorization_IllegalArgumentException() throws PaymentException {
        ListConnection conn = new ListConnection();
        ListResult result = conn.createPaymentSession("http://localhost", null, "{}");
    }

    /**
     * Create payment session invalid list data
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void createPaymentSession_invalidListData_IllegalArgumentException() throws PaymentException {
        ListConnection conn = new ListConnection();
        ListResult result = conn.createPaymentSession("http://localhost", "auth123", "");
    }

    /**
     * Gets list result invalid url
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void getListResult_invalidURL_IllegalArgumentException() throws PaymentException {
        ListConnection conn = new ListConnection();
        ListResult result = conn.getListResult(null);
    }

    /**
     * Gets language Properties invalid url
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getLanguage_invalidURL_IllegalArgumentException() {
        ListConnection conn = new ListConnection();
        Properties lang = conn.getLanguage(null, new Properties());
    }

    /**
     * Gets language Properties invalid properties
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getLanguage_invalidProperties_IllegalArgumentException() {
        ListConnection conn = new ListConnection();
        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        Properties lang = conn.getLanguage(url, null);
    }
}
