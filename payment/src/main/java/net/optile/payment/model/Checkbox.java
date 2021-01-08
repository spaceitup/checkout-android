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
 * This class is designed to hold information checkbox element that is displayed on payment page.
 */
@Getter
@Setter
public class Checkbox {
    /** Advanced API, required */
    private String name;
    /** Advanced API, required */
    @CheckboxMode.Definition
    private String mode;
    /** Advanced API, optional */
    private String requireMsg;
}
