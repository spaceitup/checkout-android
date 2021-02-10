/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.redirect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.model.Redirect;
import com.payoneer.mrs.test.util.TestUtils;

@RunWith(RobolectricTestRunner.class)
public class RedirectRequestTest {

    @Test(expected = PaymentException.class)
    public void fromOperationResult_missingRedirect() throws PaymentException {
        OperationResult operationResult = new OperationResult();
        operationResult.setLinks(createLinks());
        RedirectRequest.fromOperationResult(operationResult);
    }

    @Test(expected = PaymentException.class)
    public void fromOperationResult_missingLink() throws PaymentException {
        OperationResult operationResult = new OperationResult();
        operationResult.setRedirect(new Redirect());
        RedirectRequest.fromOperationResult(operationResult);
    }

    @Test
    public void fromOperationResult_success() throws PaymentException {
        OperationResult operationResult = new OperationResult();
        Redirect redirect = new Redirect();
        Map<String, URL> links = createLinks();
        operationResult.setRedirect(redirect);
        operationResult.setLinks(links);

        RedirectRequest request = RedirectRequest.fromOperationResult(operationResult);
        assertNotNull(request);
        assertEquals(redirect, request.getRedirect());
        assertEquals(links.get("redirect"), request.getLink());
    }

    private Map<String, URL> createLinks() {
        return Collections.singletonMap("redirect", TestUtils.createDefaultURL());
    }
}
