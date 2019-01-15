/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
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
