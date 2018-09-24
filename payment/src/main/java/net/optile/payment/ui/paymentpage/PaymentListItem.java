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
import net.optile.payment.model.InputElement;

/**
 * A Payment list item in the payment page.
 */
final class PaymentListItem {

    final int type;

    final PaymentItem item;

    boolean expanded;
    
    /** 
     * Construct a new PaymentListItem
     *
     * @param type type of this PaymentListItem
     * @param item PaymentItem to be shown
     */
    PaymentListItem(int type, PaymentItem item) {
        this.type = type;
        this.item = item;
    }

    String getCode() {
        return item.getCode();
    }
    
    String getLabel() {
        return item.getLabel();
    }

    List<InputElement> getSortedInputElements() {
        return item.getSortedInputElements();
    }
}
