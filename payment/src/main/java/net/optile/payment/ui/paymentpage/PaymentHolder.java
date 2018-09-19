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

import net.optile.payment.model.ListResult;
import java.util.List;

/**
 * Class for holding the ListResult and the list of supported PaymentMethods
 */
final class PaymentHolder {

    final ListResult listResult;

    final List<PaymentMethod> methods;

    /** 
     * Construct a new PaymentHolder object
     * 
     * @param listResult the object holding the current list session data 
     * @param methods the list of PaymentMethods supported by this Payment
     */
    PaymentHolder(ListResult listResult, List<PaymentMethod> methods) {
        this.listResult = listResult;
        this.methods = methods;
    }
}
