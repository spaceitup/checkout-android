/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to hold advanced reference information about payment.
 */
@Getter
@Setter
public class LongReference {
    /** mandatory (max 32) */
    private String essential;
    /** optional (max 32) */
    private String extended;
    /** optional (max 32) */
    private String verbose;
}
