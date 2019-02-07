/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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

    public int getMaxLength(String type) {
        ValidationGroupItem item = getGroupItem(type);
        return item != null ? item.getMaxLength() : 0;
    }
    
    public boolean isHidden(String type) {
        ValidationGroupItem item = getGroupItem(type);
        return item != null ? item.getHide() : false;
    }
    
    public String getValidationRegex(String type) {
        ValidationGroupItem item = getGroupItem(type);
        return item != null ? item.getRegex() : null;
    }

    public ValidationGroupItem getGroupItem(String type) {

        for (ValidationGroupItem item : items) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
