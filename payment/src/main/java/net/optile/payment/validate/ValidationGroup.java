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

package net.optile.payment.validate;

import java.util.List;

/**
 * Model class holding the different validations per PaymentMethod.
 * This class is identified by the method and code of a PaymentMethod.
 */
class ValidationGroup {

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
