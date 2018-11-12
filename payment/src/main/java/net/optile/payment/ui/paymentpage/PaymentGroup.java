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

import java.net.URL;
import java.util.List;

import net.optile.payment.model.InputElement;

/**
 * A Payment list item in the payment page.
 */
final class PaymentGroup {

    final int type;
    final PaymentItem item;
    final List<InputElement> elements;

    private boolean hasExpiryDate;

    /**
     * Construct a new PaymentGroup
     *
     * @param type type of this PaymentGroup
     * @param item PaymentItem to be shown
     * @param elements containing the ordered list of InputElements
     */
    PaymentGroup(int type, PaymentItem item, List<InputElement> elements) {
        this.type = type;
        this.item = item;
        this.elements = elements;
    }

    PaymentItem getActivePaymentItem() {
        return item;
    }

    boolean isSelected() {
        return item.isSelected();
    }

    String getCode() {
        return item.getCode();
    }

    String getLabel() {
        return item.getLabel();
    }

    URL getLink(String link) {
        return item.getLink(link);
    }

    String getButton() {
        return item.getButton();
    }

    String getRecurrence() {
        return item.getRecurrence();
    }

    String getRegistration() {
        return item.getRegistration();
    }

    boolean hasExpiryDate() {
        return this.hasExpiryDate;
    }

    void setHasExpiryDate(boolean hasExpiryDate) {
        this.hasExpiryDate = hasExpiryDate;
    }
}
