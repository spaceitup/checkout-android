/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

/**
 * Base class for payment presenters
 */
abstract class BasePaymentPresenter {

    final static int STOPPED = 0;
    final static int STARTED = 1;
    final static int PROCESS = 2;
    final static int REDIRECT = 3;

    final String listUrl;
    int state;

    /**
     * Construct a new BasePaymentPresenter
     *
     * @param listUrl self URL of the ListResult
     */
    BasePaymentPresenter(String listUrl) {
        this.listUrl = listUrl;
    }

    boolean checkState(int state) {
        return this.state == state;
    }

    void setState(int state) {
        this.state = state;
    }
}
