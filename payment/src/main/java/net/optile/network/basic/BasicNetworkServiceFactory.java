/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.NetworkServiceFactory;

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
    public NetworkService createService() {
        return new BasicNetworkService();
    }
}
