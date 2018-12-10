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

import java.util.List;
import java.util.ArrayList;

/**
 * Class representing one settings item in the validator settings
 */
public class SettingsItem {

    private String code;
    private String method;
    private List<Validation> validations; 
    
    public SettingsItem() {
    }
    
    public String getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }

    public Validation getValidation(String type) {

        for (Validation validation : validations) {
            if (validation.getType().equals(type)) {
                return validation;
            }
        }
        return null;
    }
}
