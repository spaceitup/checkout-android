/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import net.optile.payment.ui.model.PaymentCard;

/**
 * Class representing an item in the PaymentList
 */
abstract class ListItem {

    int viewType;

    ListItem(int viewType) {
        this.viewType = viewType;
    }

    boolean hasPaymentCard() {
        return false;
    }

    PaymentCard getPaymentCard() {
        return null;
    }
}
