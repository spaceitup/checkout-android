/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.ui.service.basic.BasicNetworkService;

/**
 * Factory for providing PaymentNetworkServices for networks.
 */
public class PaymentNetworkServiceFactory {

    private final static Map<String, PaymentNetworkService> cache = new ConcurrentHashMap<>();
    
    /** 
     * Get the PaymentNetworkService given the PaymentNetwork code
     * 
     * @param code the code of the PaymentNetwork
     * @return the service or null if not found
     */
    public static PaymentNetworkService getService(String code, String method) {

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
                return new BasicNetworkService();
        }
        return null;
    }
}
