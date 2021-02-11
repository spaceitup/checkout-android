/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.resource;

import java.util.List;
import java.util.Map;

/**
 * Class containing filters for which PaymentMethods should be combined in the payment page.
 */
public class PaymentGroup {

    private List<PaymentGroupItem> items;

    public void setPaymentGroupItems(List<PaymentGroupItem> items) {
        this.items = items;
    }

    /**
     * The code of the first filter in the payment group is used to uniquely identify this group
     *
     * @return the id of this PaymentGroup
     */
    public String getId() {
        return items != null ? items.get(0).getCode() : null;
    }

    /**
     * populate the lookup table with each PaymentGroupItem code mapping to this group
     */
    public void populate(Map<String, PaymentGroup> map) {
        if (items == null) {
            return;
        }
        for (PaymentGroupItem item : items) {
            map.put(item.getCode(), this);
        }
    }

    /**
     * Get the smart selection regex given the payment code.
     *
     * @param code used to lookup the smart selection regex
     * @return the regex for smart selection or null if not found
     */
    public String getSmartSelectionRegex(String code) {
        if (items == null) {
            return null;
        }
        for (PaymentGroupItem item : items) {

            if (item.getCode().equals(code)) {
                return item.getRegex();
            }
        }
        return null;
    }
}
