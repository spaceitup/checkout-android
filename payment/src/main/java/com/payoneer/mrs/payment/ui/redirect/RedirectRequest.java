/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.redirect;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.model.Redirect;

import java.net.URL;
import java.util.Map;

/**
 * Class holding the data for making a redirect
 */
public final class RedirectRequest {

    private final Redirect redirect;
    private final URL link;

    /**
     * Construct a new RedirectRequest
     *
     * @param redirect contains the Redirect information
     * @param link pointing to the redirect api endpoint
     */
    public RedirectRequest(Redirect redirect, URL link) {
        this.redirect = redirect;
        this.link = link;
    }

    /**
     * Get the redirect method
     *
     * @return the redirect method
     */
    public String getRedirectMethod() {
        return redirect.getMethod();
    }

    /**
     * Get the Redirect Object
     *
     * @return the redirect object
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * Get the redirect link
     *
     * @return the redirect link
     */
    public URL getLink() {
        return link;
    }

    /**
     * Create a RedirectRequest from the provided OperationResult
     *
     * @param operationResult containing the redirect request information
     * @return newly created RedirectRequest
     * @throws PaymentException when the OperationResult does not contain the required information
     */
    public static RedirectRequest fromOperationResult(OperationResult operationResult) throws PaymentException {
        Redirect redirect = operationResult.getRedirect();
        if (redirect == null) {
            throw new PaymentException("OperationResult must contain a Redirect object");
        }
        Map<String, URL> links = operationResult.getLinks();
        if (links == null || !links.containsKey("redirect")) {
            throw new PaymentException("OperationResult must contain a redirect link URL");
        }
        return new RedirectRequest(redirect, links.get("redirect"));
    }
}
