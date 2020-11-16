/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.resource;

/**
 * Class holding a filter for a PaymentMethod inside a payment group
 */
public class PaymentGroupItem {

    private String code;
    private String regex;

    private PaymentGroupItem() {
    }

    public String getCode() {
        return code;
    }

    public String getRegex() {
        return this.regex;
    }
}
