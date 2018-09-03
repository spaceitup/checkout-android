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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.optile.payment.network.NetworkResponse;
import net.optile.payment.network.NetworkError;

import static org.junit.Assert.*;

public class ListConnectionTest {

    private ListConnection conn;
    
    @Before
    public void setUp() throws Exception {
        conn = new ListConnection("http://optile.net");
    }

    @After
    public void tearDown() throws Exception {
        conn = null;
    }

    @Test
    public void createListSession_missingAuthorization_invalidValueError() {
    }
}
