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

/**
 * A Payment list item in the payment page.
 */
final class PaymentListItem {

    final int type;

    final PaymentMethod method;

    /** 
     * Construct a new PaymentListItem
     *
     * @param type   type of this PaymentListItem
     * @param method PaymentMethod to be shown
     */
    PaymentListItem(int type, PaymentMethod method) {
        this.type = type;
        this.method = method;
    }

    String getCode() {
        return method.getCode();
    }
    
    String getLabel() {
        return method.getLabel();
    }
}
