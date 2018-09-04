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

import android.text.TextUtils;

import net.optile.payment.network.NetworkError.ErrorType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class ListConnectionTest {

    private ListConnection conn;
    
    @Before
    public void setUp() throws Exception {
        conn = new ListConnection("http://optile.net");

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
    public void createPaymentSession_invalidAuthorization_invalidValueError() {

        NetworkResponse resp = conn.createPaymentSession(null, "{}");
        assertTrue(resp.isError(ErrorType.INVALID_VALUE));
    }

    @Test
    public void createPaymentSession_invalidListData_invalidValueError() {

        NetworkResponse resp = conn.createPaymentSession("abc123", "");
        assertTrue(resp.isError(ErrorType.INVALID_VALUE));
    }

    @Test
    public void getListResult_invalidURL_invalidValueError() {

        NetworkResponse resp = conn.getListResult(null);
        assertTrue(resp.isError(ErrorType.INVALID_VALUE));
    }
}
