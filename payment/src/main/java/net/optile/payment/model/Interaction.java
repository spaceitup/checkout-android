/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

/**
 * This class is designed to hold interaction information that prescribes further reaction of merchant portal to this transaction or operation.
 */
public class Interaction {
    /** Simple API, always present */
    @InteractionCode.Definition
    private String code;
    /** Simple API, always present */
    @InteractionReason.Definition
    private String reason;

    /**
     * Gets value of code.
     *
     * @return the code.
     */
    @InteractionCode.Definition
    public String getCode() {
        return code;
    }

    /**
     * Sets value of code.
     *
     * @param code the code to set.
     */
    public void setCode(@InteractionCode.Definition String code) {
        this.code = code;
    }

    /**
     * Gets value of reason.
     *
     * @return the reason.
     */
    @InteractionReason.Definition
    public String getReason() {
        return reason;
    }

    /**
     * Sets value of reason.
     *
     * @param reason the reason to set.
     */
    public void setReason(@InteractionReason.Definition String reason) {
        this.reason = reason;
    }
}
