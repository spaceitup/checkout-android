/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;

/**
 * Class containing PaymentMethodCodes for which the accountNumber and verificationCodes are custom validated.
 * Custom validation settings are defined in the R.raw.validations file.
 */
public class PaymentMethodCodes {

    public final static String AMEX = "AMEX";
    public final static String CASTORAMA = "CASTORAMA";
    public final static String DINERS = "DINERS";
    public final static String DISCOVER = "DISCOVER";
    public final static String MASTERCARD = "MASTERCARD";
    public final static String UNIONPAY = "UNIONPAY";
    public final static String VISA = "VISA";
    public final static String VISA_DANKORT = "VISA_DANKORT";
    public final static String VISAELECTRON = "VISAELECTRON";
    public final static String CARTEBANCAIRE = "CARTEBANCAIRE";
    public final static String MAESTRO = "MAESTRO";
    public final static String MAESTROUK = "MAESTROUK";
    public final static String POSTEPAY = "POSTEPAY";
    public final static String SEPADD = "SEPADD";
    public final static String JCB = "JCB";    

    /**
     * Check if the given type is a valid payment input type
     *
     * @param type the payment input type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String type) {

        if (type != null) {
            switch (type) {
                case AMEX:
                case CASTORAMA:
                case DINERS:
                case DISCOVER:
                case MASTERCARD:
                case UNIONPAY:
                case VISA:
                case VISA_DANKORT:
                case VISAELECTRON:
                case CARTEBANCAIRE:
                case MAESTRO:
                case MAESTROUK:
                case POSTEPAY:
                case SEPADD:
                case JCB:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        AMEX,
        CASTORAMA,
        DINERS,
        DISCOVER,
        MASTERCARD,
        UNIONPAY,
        VISA,
        VISA_DANKORT,
        VISAELECTRON,
        CARTEBANCAIRE,
        MAESTRO,
        MAESTROUK,
        POSTEPAY,
        SEPADD,
        JCB
    })
    public @interface Definition { }
}
