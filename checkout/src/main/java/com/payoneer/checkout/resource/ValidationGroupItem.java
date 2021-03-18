/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.resource;

/**
 * Model class holding the validation Regex for the PaymentInputType
 */
public class ValidationGroupItem {

    private String type;
    private String regex;
    private boolean hide;
    private int maxLength;

    private ValidationGroupItem() {
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getType() {
        return type;
    }

    public String getRegex() {
        return regex;
    }

    public boolean getHide() {
        return hide;
    }
}
