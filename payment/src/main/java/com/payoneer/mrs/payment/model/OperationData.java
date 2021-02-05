/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to hold information for operation (CHARGE, PAYOUT, UPDATE) with selected payment network.
 */
@Getter
@Setter
public class OperationData {
    /** Simple API, optional */
    private AccountInputData account;
    /** Simple API, optional */
    private Boolean autoRegistration;
    /** Simple API, optional */
    private Boolean allowRecurrence;
    /** Advanced API, optional */
    private Map<String, Boolean> checkboxes;
    /** Provider request parameters. */
    private ProviderParameters providerRequest;
    /** Customer web browser data */
    private BrowserData browserData;
}
