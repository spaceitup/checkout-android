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

package net.optile.payment.ui.paymentpage;

import java.util.List;

/**
 * Class containing PaymentCards that belong to one section 
 */
class PaymentListSection {

    final List<PaymentCard> items;
    final String label;
    
    PaymentListSection(String label, List<PaymentCard> items) {
        this.label = label;
        this.items = items;
    }

    int getSize() {
        items.size() + 1;
    }
}
