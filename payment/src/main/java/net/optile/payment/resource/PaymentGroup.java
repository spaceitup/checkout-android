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

package net.optile.payment.resource;

import java.util.List;
import java.util.Map;

/**
 * Class containing filters for which PaymentMethods should be combined in the payment page.
 */
public class PaymentGroup {

    private List<PaymentGroupItem> items;

    private PaymentGroup() {
    }

    /** 
     * The code of the first filter in the payment group is used to uniquely identify this group 
     * 
     * @return the id of this PaymentGroup 
     */
    public String getId() {
        return items.get(0).getCode();
    }

    /** 
     * populate the lookup table with each PaymentGroupItem code mapping to this group
     * 
     * @param map the lookup table to fill
     */
    public Map<String, PaymentGroup> populate(Map<String, PaymentGroup> map) {

        for (PaymentGroupItem item : items) {
            map.put(item.getCode(), this);
        }
        return map;
    }

    /** 
     * Get the smart selection regex given the payment code.
     * 
     * @param code used to lookup the smart selection regex
     * @return the regex for smart selection or null if not found 
     */
    public String getSmartSelectionRegex(String code) {
        
        for (PaymentGroupItem item : items) {

            if (item.getCode().equals(code)) {
                return item.getRegex();
            }
        }
        return null;
    }
}
