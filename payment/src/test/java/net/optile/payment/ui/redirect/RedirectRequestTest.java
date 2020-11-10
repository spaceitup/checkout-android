/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;

@RunWith(RobolectricTestRunner.class)
public class RedirectRequestTest {

    @Test(expected = PaymentException.class)
    public void fromOperationResult_missingRedirect() throws PaymentException, MalformedURLException {
        OperationResult operationResult = new OperationResult();
        operationResult.setLinks(createLinks());
        RedirectRequest.fromOperationResult(operationResult);
    }

    @Test(expected = PaymentException.class)
    public void fromOperationResult_missingLink() throws PaymentException, MalformedURLException {
        OperationResult operationResult = new OperationResult();
        operationResult.setRedirect(new Redirect());
        RedirectRequest.fromOperationResult(operationResult);
    }

    @Test
    public void fromOperationResult_success() throws PaymentException, MalformedURLException {
        OperationResult operationResult = new OperationResult();
        operationResult.setRedirect(new Redirect());
        operationResult.setLinks(createLinks());
        RedirectRequest.fromOperationResult(operationResult);
    }

    private Map<String, URL> createLinks() throws MalformedURLException {
        Map<String, URL> links = new HashMap<>();
        URL link = new URL("http://example.com");
        links.put("redirect", link);
        return links;
    }
}
