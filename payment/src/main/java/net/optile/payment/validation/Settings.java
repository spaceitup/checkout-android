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
 * The class representing the validation settings holding the individual validation 
 * regex for validating the different input values
 */
public class Settings {

    private List<SettingsItem> items;

    public Settings() {
        this.items = new ArrayList<>();
    }
    
    public Validation getValidation(String method, String code, String type) {

        for (SettingsItem item : items) {

            if (item.matches(method, code)) {
                return item.getValidation(type);
            }
        }
        return null;
    }
}

