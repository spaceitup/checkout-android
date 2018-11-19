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

package net.optile.payment.ui.list;

import net.optile.payment.ui.model.PaymentCard;

/**
 * Class representing an item in the PaymentList
 */
class ListItem {

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
