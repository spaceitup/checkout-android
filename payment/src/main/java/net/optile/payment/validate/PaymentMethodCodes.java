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

package net.optile.payment.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;

/**
 * Class defining the PaymentMethodCodes used for validation in this Payment SDK.
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
    public final static String SOLO = "SOLO";

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
            case SOLO:
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
                SOLO
                })
                public @interface Definition { }
}
