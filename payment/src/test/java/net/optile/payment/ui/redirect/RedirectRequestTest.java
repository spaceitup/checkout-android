/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;
import net.optile.test.util.TestUtils;

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
