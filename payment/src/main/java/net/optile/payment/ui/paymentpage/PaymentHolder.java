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
import java.util.Properties;

import net.optile.payment.model.ListResult;

/**
 * Class for holding the ListResult and the list of supported PaymentMethods
 */
final class PaymentHolder {

    final ListResult listResult;

    final List<PaymentItem> items;

    private Properties language;

    /**
     * Construct a new PaymentHolder object
     *
     * @param listResult Object holding the current list session data
     * @param items list of PaymentItems supported by the current Payment session
     */
    PaymentHolder(ListResult listResult, List<PaymentItem> items) {
        this.listResult = listResult;
        this.items = items;
    }

    void setLanguage(Properties language) {
        this.language = language;
    }

    String translate(String key, String defValue) {
        return language != null ? language.getProperty(key, defValue) : defValue;
    }
}
