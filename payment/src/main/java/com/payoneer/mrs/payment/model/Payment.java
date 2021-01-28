/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to hold payment information.
 */
@Getter
@Setter
public class Payment {
    /** mandatory */
    private String reference;
    /** mandatory */
    private BigDecimal amount;
    /** mandatory */
    private String currency;
    /** optional (max 128) */
    private String invoiceId;
    /** optional */
    private LongReference longReference;
}
