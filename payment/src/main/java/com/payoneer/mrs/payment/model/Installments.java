/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Installments information.
 */
@Getter
@Setter
public class Installments {
    /** payment amount of original payment */
    private PaymentAmount originalPayment;
    /** installments plans */
    private List<InstallmentsPlan> plans;
}
