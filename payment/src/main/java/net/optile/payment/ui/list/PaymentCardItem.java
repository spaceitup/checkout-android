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
 * Class representing a PaymentCard item in the RecyclerView
 */
final class PaymentCardItem extends ListItem {

    PaymentCard paymentCard;

    PaymentCardItem(int viewType, PaymentCard paymentCard) {
        super(viewType);
        this.paymentCard = paymentCard;
    }

    PaymentCard getPaymentCard() {
        return paymentCard;
    }

    boolean hasPaymentCard() {
        return true;
    }
}
