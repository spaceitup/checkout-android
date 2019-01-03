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

/**
 * Model class holding the validation per PaymentMethod.
 * This class is identified by the code of a PaymentMethod.
 */
public class ValidationGroup {

    private String code;
    private List<ValidationGroupItem> items;
    
    private ValidationGroup() {
    }
        
    public String getCode() {
        return code;
    }
    
    public boolean matches(String code) {
        return this.code.equals(code);
    }

    public String getValidationRegex(String type) {

        for (ValidationGroupItem item : items) {
            if (item.getType().equals(type)) {
                return item.getRegex();
            }
        }
        return null;
    }
}
