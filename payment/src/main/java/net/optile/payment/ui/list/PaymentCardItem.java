/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import net.optile.payment.ui.model.PaymentCard;

/**
 * Class representing a PaymentCard item in the RecyclerView
 */
final class PaymentCardItem extends ListItem {

    final PaymentCard paymentCard;

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
