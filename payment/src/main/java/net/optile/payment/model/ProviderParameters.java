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

import java.util.List;

/**
 * Describes a collection of provider specific parameters.
 */
public class ProviderParameters {
    /** optional, provider code. */
    private String providerCode;
    /** collection of parameters. */
    private List<Parameter> parameters;

    /**
     * Gets provider code these parameters are intended for.
     *
     * @return the provider code.
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * Sets provider code these parameters are intended for.
     *
     * @param providerCode the provider code to set.
     */
    public void setProviderCode(final String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * Gets collection of provider specific parameters.
     *
     * @return the provider parameters.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets collection of provider specific parameters.
     *
     * @param parameters the provider parameters to set.
     */
    public void setParameters(final List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
