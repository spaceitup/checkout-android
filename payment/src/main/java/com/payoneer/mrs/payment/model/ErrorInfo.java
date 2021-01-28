/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to hold information about error that happened during processing of either list or operation request.
 */
@Getter
@Setter
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
}
