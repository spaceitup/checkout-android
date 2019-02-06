/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold information about operation result.
 */
public class OperationResult {
    /** PCI API, always present */
    private String resultInfo;
    /** PCI API, optional, always present in response to action (POST, UPDATE) */
    private Interaction interaction;
    /** PCI API, optional */
    private Redirect redirect;
    /** Provider response parameters. */
    private ProviderParameters providerResponse;

    /**
     * Gets value of resultInfo.
     *
     * @return the resultInfo.
     */
    public String getResultInfo() {
        return resultInfo;
    }

    /**
     * Sets value of resultInfo.
     *
     * @param resultInfo the resultInfo to set.
     */
    public void setResultInfo(final String resultInfo) {
        this.resultInfo = resultInfo;
    }

    /**
     * Gets value of interaction.
     *
     * @return the interaction.
     */
    public Interaction getInteraction() {
        return interaction;
    }

    /**
     * Sets value of interaction.
     *
     * @param interaction the interaction to set.
     */
    public void setInteraction(final Interaction interaction) {
        this.interaction = interaction;
    }

    /**
     * Gets value of redirect.
     *
     * @return the redirect.
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * Sets value of redirect.
     *
     * @param redirect the redirect to set.
     */
    public void setRedirect(final Redirect redirect) {
        this.redirect = redirect;
    }

    /**
     * Gets provider response parameters.
     *
     * @return Provider response parameters.
     */
    public ProviderParameters getProviderResponse() {
        return providerResponse;
    }

    /**
     * Sets provider response parameters.
     *
     * @param providerResponse Provider response parameters.
     */
    public void setProviderResponse(final ProviderParameters providerResponse) {
        this.providerResponse = providerResponse;
    }
}
