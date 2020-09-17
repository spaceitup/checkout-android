/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold information about error that happened during processing of either list or operation request.
 */
public class ErrorInfo {
    /** Simple API, always present */
    private String resultInfo;
    /** Simple API, always present */
    private Interaction interaction;

    /**
     * Construct an empty ErrorInfo
     */
    public ErrorInfo() {
    }

    /**
     * Constructs a new ErrorInfo with the resultInfo and interaction
     *
     * @param resultInfo providing a description of the error
     * @param interaction contains recommendation which steps to take next
     */
    public ErrorInfo(String resultInfo, Interaction interaction) {
        this.resultInfo = resultInfo;
        this.interaction = interaction;
    }

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
    public void setResultInfo(String resultInfo) {
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
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }
}
