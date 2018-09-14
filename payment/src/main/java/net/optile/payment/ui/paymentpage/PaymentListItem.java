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

import net.optile.payment.model.ApplicableNetwork;
import java.util.List;

/**
 * A Payment list item in the payment page. This item may contain multiple ApplicableNetwork elements.
 */
final class PaymentListItem {

    ApplicableNetwork network;

    int type;
    
    int index;

    /** 
     * Construct a new PaymentListItem
     *
     * @param type    the type of this PaymentListItem
     * @param network the ApplicableNetwork to be shown
     */
    PaymentListItem(int type, ApplicableNetwork network) {
        this.type = type;
        this.network = network;
    }
}
