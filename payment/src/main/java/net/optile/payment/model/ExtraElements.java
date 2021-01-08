/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to hold information about extra elements that should be displayed on payment page.
 */
@Getter
@Setter
public class ExtraElements {
    private List<ExtraElement> top;
    private List<ExtraElement> bottom;
}
