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

    /** The list of networks presented in this item */
    List<ApplicableNetwork> networks;

    /** The type of this item */
    int type;
    
    /** The currently selected network in this item */
    int index;

    /** 
     * Construct a PaymentListItem
     *
     * @param type the type of this PaymentListItem
     * @param 
     */
    PaymentListItem(int type, List<ApplicableNetwork> networks) {
        this.type = type;
        this.networks = networks;
    }
}
