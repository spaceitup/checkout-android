/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import android.text.TextUtils;
import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.model.ApplicableNetwork;
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
    public boolean isNetworkSupported(ApplicableNetwork network) {
        return isCodeSupported(network.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCodeSupported(String code) {
        switch (code) {
            case PaymentNetworkCodes.AMEX:
            case PaymentNetworkCodes.CASTORAMA:
            case PaymentNetworkCodes.DINERS:
            case PaymentNetworkCodes.DISCOVER:
            case PaymentNetworkCodes.MASTERCARD:
            case PaymentNetworkCodes.UNIONPAY:
            case PaymentNetworkCodes.VISA:
            case PaymentNetworkCodes.VISA_DANKORT:
            case PaymentNetworkCodes.VISAELECTRON:
            case PaymentNetworkCodes.CARTEBANCAIRE:
            case PaymentNetworkCodes.MAESTRO:
            case PaymentNetworkCodes.MAESTROUK:
            case PaymentNetworkCodes.POSTEPAY:
            case PaymentNetworkCodes.SEPADD:
            case PaymentNetworkCodes.JCB:
                return true;
            default:
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkService createService() {
        return new BasicNetworkService();
    }
}
