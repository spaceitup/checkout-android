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
 * Class representing an account in the PaymentList
 */
class AccountCardItem extends ListItem {

    AccountCard account;

    AccountCardItem(int viewType, AccountCard account) {
        super(viewType);
        this.account = account;
    }
}
