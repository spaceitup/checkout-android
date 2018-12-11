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

import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import net.optile.payment.util.GsonHelper;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.R;

/**
 * The class representing the list of validations holding the individual validation 
 * regex for validating the different input values
 */
final class Validations {

    private List<ValidationHolder> items;

    private Validations() {
        items = new ArrayList<>();
    }
    
    Validation get(String method, String code, String type) {

        for (ValidationHolder item : items) {

            if (item.matches(method, code)) {
                return item.getValidation(type);
            }
        }
        return null;
    }

    static class ValidationHolder {
        private String method;
        private String code;
        private List<Validation> validations; 

        boolean matches(String method, String code) {
            return (this.method.equals(method)) && (this.code.equals(code));
        }

        Validation getValidation(String type) {
            
            for (Validation validation : validations) {
                if (validation.getType().equals(type)) {
                    return validation;
                }
            }
            return null;
        }
    }
}
