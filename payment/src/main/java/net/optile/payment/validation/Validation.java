/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.validation;

/**
 * Class holding the validation regex for the PaymentInputType
 */
public class Validation {

    private String type;
    private String regex;

    public Validation() {
    }

    public String getType() {
        return type;
    }

    public String getRegex() {
        return regex;
    }
}
