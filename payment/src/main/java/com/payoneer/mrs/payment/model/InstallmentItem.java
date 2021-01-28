/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * An information about particular payment what is involved into installment payment process.
 */
@Getter
@Setter
public class InstallmentItem {
    /** The amount of installment (mandatory) */
    private BigDecimal amount;
    /** Installment/payment date */
    private Date date;
}
