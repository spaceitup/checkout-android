/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadLanguageFile_invalidURL_IllegalArgumentException() throws PaymentException {
        ListConnection conn = new ListConnection();
        Properties prop = conn.loadLanguageFile(new Properties(), null);
    }

    private URL createTestURL() {
        URL url = null;

        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertNotNull(url);
        return url;
    }
}
