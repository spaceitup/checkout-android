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

import androidx.annotation.StringDef;

/**
 * Class containing the different payment input types i.e. number, iban, bic etc.
 */
public class PaymentInputType {

    public final static String ACCOUNT_NUMBER = "number";
    public final static String HOLDER_NAME = "holderName";
    public final static String EXPIRY_DATE = "expiryDate";
    public final static String EXPIRY_MONTH = "expiryMonth";
    public final static String EXPIRY_YEAR = "expiryYear";
    public final static String VERIFICATION_CODE = "verificationCode";
    public final static String BANK_CODE = "bankCode";
    public final static String IBAN = "iban";
    public final static String BIC = "bic";
    public final static String ALLOW_RECURRENCE = "allowRecurrence";
    public final static String AUTO_REGISTRATION = "autoRegistration";

    /**
     * Check if the given type is a valid payment input type
     *
     * @param type the payment input type to validate
     * @return true when valid, false otherwise
     */
    public static boolean isValid(final String type) {

        if (type != null) {
            switch (type) {
                case ACCOUNT_NUMBER:
                case HOLDER_NAME:
                case EXPIRY_DATE:
                case EXPIRY_MONTH:
                case EXPIRY_YEAR:
                case VERIFICATION_CODE:
                case BANK_CODE:
                case IBAN:
                case BIC:
                case ALLOW_RECURRENCE:
                case AUTO_REGISTRATION:
                    return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        ACCOUNT_NUMBER,
        HOLDER_NAME,
        EXPIRY_DATE,
        EXPIRY_MONTH,
        EXPIRY_YEAR,
        VERIFICATION_CODE,
        BANK_CODE,
        IBAN,
        BIC,
        ALLOW_RECURRENCE,
        AUTO_REGISTRATION
    })
    public @interface Definition { }
}

