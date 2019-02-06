/*
 * Copyright (c) 2019 optile GmbH
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

}
