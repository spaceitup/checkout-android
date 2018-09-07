/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;
import java.util.List;

import android.support.annotation.StringDef;

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
