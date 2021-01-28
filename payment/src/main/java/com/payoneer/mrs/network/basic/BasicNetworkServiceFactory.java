/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.network.basic;

import com.payoneer.mrs.payment.core.PaymentNetworkCodes;
import com.payoneer.mrs.payment.model.PaymentMethod;
import com.payoneer.mrs.payment.ui.service.NetworkService;
import com.payoneer.mrs.payment.ui.service.NetworkServiceFactory;

import android.content.Context;

/**
 * Specific implementation for basic networks like i.e. Visa, mastercard and sepa.
 */
public final class BasicNetworkServiceFactory implements NetworkServiceFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String code, String method) {
        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                return true;
            default:
                return supportsCode(code);
        }
    }

    private boolean supportsCode(String code) {
        switch (code) {
            case PaymentNetworkCodes.SEPADD:
            case PaymentNetworkCodes.PAYPAL:
            case PaymentNetworkCodes.WECHATPC_R:
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkService createService(Context context) {
        return new BasicNetworkService(context);
    }
}
