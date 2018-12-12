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
 * Model class containing the list of validate groups, each group contains the validations per PaymentMethod.
 */
final class Validations {

    private List<ValidationGroup> groups;

    private Validations() {
    }
    
    Validation get(String method, String code, String type) {

        if (groups == null) {
            return null;
        }
        for (ValidationGroup group : groups) {

            if (group.matches(method, code)) {
                return group.getValidation(type);
            }
        }
        return null;
    }
}
