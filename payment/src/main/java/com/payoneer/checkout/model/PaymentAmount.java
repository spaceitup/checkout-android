/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Payment amount data.
 */
@Getter
@Setter
public class PaymentAmount {
    /** amount */
    private BigDecimal amount;
    /** currency */
    private String currency;
}
