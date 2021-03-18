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
 * Option description.
 */
@Getter
@Setter
public class SelectOption {
    /** value */
    private String value;
    /** a flag for the option to be preselected - shown first in the drop-down list */
    private Boolean selected;
}
