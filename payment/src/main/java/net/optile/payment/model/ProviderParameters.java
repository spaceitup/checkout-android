/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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
