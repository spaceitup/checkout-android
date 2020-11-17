/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.net.URL;
import java.util.List;

/**
 * This class is designed to hold information to redirect customers browser as a result of operation execution.
 */
public class Redirect {

    /** Simple API, always present */
    private URL url;
    /** Simple API, always present */
    @HttpMethod.Definition
    private String method;
    /** Simple API, optional */
    private List<Parameter> parameters;
    /** Simple API, optional */
    private Boolean suppressIFrame;
    /** Simple API, always present in new transactions */
    private String type;

    /**
     * Gets value of url.
     *
     * @return the url.
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Sets value of url.
     *
     * @param url the url to set.
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Gets value of method.
     *
     * @return the method.
     */
    @HttpMethod.Definition
    public String getMethod() {
        return method;
    }

    /**
     * Sets value of method.
     *
     * @param method the method to set.
     */
    public void setMethod(@HttpMethod.Definition String method) {
        this.method = method;
    }

    /**
     * Gets value of parameters.
     *
     * @return the parameters.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets value of parameters.
     *
     * @param parameters the parameters to set.
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets value of suppressIFrame.
     *
     * @return the suppressIFrame.
     */
    public Boolean getSuppressIFrame() {
        return suppressIFrame;
    }

    /**
     * Sets value of suppressIFrame.
     *
     * @param suppressIFrame the suppressIFrame to set.
     */
    public void setSuppressIFrame(Boolean suppressIFrame) {
        this.suppressIFrame = suppressIFrame;
    }

    /**
     * Gets type of this redirect.
     * <p>
     * Possible values are:
     * <ul>
     * <li><code>SUMMARY</code> - redirect points to the session's <code>summaryUrl</code>
     * <li><code>RETURN</code> - redirect points to the session's <code>returnUrl</code>
     * <li><code>CANCEL</code> - redirect points to the session's <code>cancelUrl</code>
     * <li><code>PROVIDER</code> - redirect to the external page of a provider (redirect method such as PayPal, Sofort, iDeal, etc.) or 3D
     * Secure pages in case of credit/debit card processing.
     * </ul>
     * Note: new types of redirect may appear in the future for new payment use cases.
     *
     * @return the redirect type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type of this redirect.
     *
     * @param type the redirect type to set.
     */
    public void setType(final String type) {
        this.type = type;
    }
}
