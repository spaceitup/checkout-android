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

import java.net.URL;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.optile.payment.network.NetworkResponse;
import net.optile.payment.network.NetworkError;
import net.optile.payment.network.NetworkError.ErrorType;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.text.TextUtils;

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class ChargeConnectionTest {

    private ChargeConnection conn;
    
    @Before
    public void setUp() throws Exception {
        conn = new ChargeConnection();

        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {

                @Override
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    CharSequence a = (CharSequence) invocation.getArguments()[0];
                    return a == null || a.length() == 0;
                }
            });
    }

    @After
    public void tearDown() throws Exception {
        conn = null;
    }

    @Test
    public void createCharge_invalidURL_invalidValueError() {

        NetworkResponse resp = conn.createCharge(null, "{}");
        assertTrue(resp.hasError());
        assertTrue(resp.isError(ErrorType.INVALID_VALUE));
    }

    @Test
    public void createCharge_invalidChargeData_invalidValueError() {

        URL url = null;
        try {
            url = new URL("http://optile.net");
        } catch (MalformedURLException e) {
        }

        assertNotNull(url);

        NetworkResponse resp = conn.createCharge(url, "");
        assertTrue(resp.isError(ErrorType.INVALID_VALUE));
    }
}
