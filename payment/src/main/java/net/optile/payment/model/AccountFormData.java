/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Account data what should be used to pre-fill payment form.
 */
@Getter
@Setter
public class AccountFormData {

    /** holder name */
    private String holderName;
}
