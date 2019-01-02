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

package net.optile.payment.ui.group;

import java.util.List;

/**
 * Class containing filters for which PaymentMethods should be combined in the payment page.
 */
public class NetworkGroup {

    private List<NetworkFilter> filters; 

    private PaymentGroup() {
    }

    public boolean contains(String code) {
        return false;
    }
    
    /** 
     * Get the payment method given the value, if more than one filter can be applied to the input value then return null.
     * 
     * @param value input value to verify by each filter 
     * @return the payment code or null if there is no unique filter matching the input value
     */
    public String getPaymentCode(String value) {

        if (value == null) {
            return null;
        }
        String code = null;
        
        for (GroupFilter filter : filters) {

            if (filter.matches(value)) {
                if (code != null) {
                    return null;
                }
                code = filter.getCode();
            }
        }
        return code;
    }
}
