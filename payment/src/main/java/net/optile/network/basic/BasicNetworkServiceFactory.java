/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.network.basic;

import android.text.TextUtils;
import java.util.Objects;
import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.model.ApplicableNetwork;
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
    public boolean isSupported(String code, String method) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(method);

        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                return true;
            default:
                return isCodeSupported(code);
        }
    }

    private boolean isCodeSupported(String code) {
        switch (code) {
            case PaymentNetworkCodes.SEPADD:
            case PaymentNetworkCodes.PAYPAL:
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
