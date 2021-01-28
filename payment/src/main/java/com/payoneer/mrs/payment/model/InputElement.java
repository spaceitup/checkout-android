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
 * Form input element description.
 */
@Getter
@Setter
public class InputElement {
    /** name */
    private String name;
    /** type */
    private String type;
    /** options */
    private List<SelectOption> options;
}
