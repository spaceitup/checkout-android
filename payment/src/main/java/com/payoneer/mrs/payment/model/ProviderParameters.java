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
 * Describes a collection of provider specific parameters.
 */
@Getter
@Setter
public class ProviderParameters {
    /** optional, provider code. */
    private String providerCode;
    /** collection of parameters. */
    private List<Parameter> parameters;
}
