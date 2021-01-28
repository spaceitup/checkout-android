/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.page;

import com.payoneer.mrs.payment.form.Operation;
import com.payoneer.mrs.payment.ui.model.PaymentSession;

/**
 * The PaymentListView is the interface is the View part of the MVP, this is implemented by the PaymentListActivity
 */
interface PaymentListView extends PaymentView {

    /**
     * Clear the payment list
     */
    void clearList();

    /**
     * Show the Charge payment screen for the provided operation
     *
     * @param requestCode the code identifying the request
     * @param operation to be handled by the charge payment screen
     */
    void showChargePaymentScreen(int requestCode, Operation operation);

    /**
     * Stop loading and show the PaymentSession
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);
}
