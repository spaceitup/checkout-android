/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import java.net.URL;
import java.util.Map;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;

/**
 * Class holding the data for making a Request request
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
     * Get the Redirect method for this Redirect request
     *
     * @return the redirect method
     */
    public String getRedirectMethod() {
        return redirect.getMethod();
    }

    /**
     * Get the Redirect Object from this redirect request
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
    public final static RedirectRequest fromOperationResult(OperationResult operationResult) throws PaymentException {
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
