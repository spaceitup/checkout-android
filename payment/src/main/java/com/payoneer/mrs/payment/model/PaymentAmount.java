/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

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
