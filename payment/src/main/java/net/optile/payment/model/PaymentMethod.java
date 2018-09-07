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

package net.optile.payment.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.support.annotation.StringDef;

/**
 * This class describes the payment methods
 */
public class PaymentMethod {

    public final static String BANK_TRANSFER = "BANK_TRANSFER";
    public final static String BILLING_PROVIDER = "BILLING_PROVIDER";
    public final static String CASH_ON_DELIVERY = "CASH_ON_DELIVERY";
    public final static String CHECK_PAYMENT = "CHECK_PAYMENT";
    public final static String CREDIT_CARD = "CREDIT_CARD";
    public final static String DEBIT_CARD = "DEBIT_CARD";
    public final static String DIRECT_DEBIT = "DIRECT_DEBIT";
    public final static String ELECTRONIC_INVOICE = "ELECTRONIC_INVOICE";
    public final static String GIFT_CARD = "GIFT_CARD";
    public final static String MOBILE_PAYMENT = "MOBILE_PAYMENT";
    public final static String ONLINE_BANK_TRANSFER = "ONLINE_BANK_TRANSFER";
    public final static String OPEN_INVOICE = "OPEN_INVOICE";
    public final static String PREPAID_CARD = "PREPAID_CARD";
    public final static String TERMINAL = "TERMINAL";
    public final static String WALLET = "WALLET";
    public final static String INVALID_VALUE = "InvalidValue";

    /**
     * The interface Definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ BANK_TRANSFER,
        BILLING_PROVIDER,
        CASH_ON_DELIVERY,
        CHECK_PAYMENT,
        CREDIT_CARD,
        DEBIT_CARD,
        DIRECT_DEBIT,
        ELECTRONIC_INVOICE,
        GIFT_CARD,
        MOBILE_PAYMENT,
        ONLINE_BANK_TRANSFER,
        OPEN_INVOICE,
        PREPAID_CARD,
        TERMINAL,
        METHOD_WALLET })
    public @interface Definition {}

    /**
     * Gets method as a checked value.
     * If the value does not match any predefined methods then return
     * INVALID_VALUE.
     *
     * @return the checked method
     */
    @PaymentMethod
    public String getCheckedMethod() {

        if (this.method != null) {
            switch (this.method) {
                case METHOD_BANK_TRANSFER:
                case METHOD_BILLING_PROVIDER:
                case METHOD_CASH_ON_DELIVERY:
                case METHOD_CHECK_PAYMENT:
                case METHOD_CREDIT_CARD:
                case METHOD_DEBIT_CARD:
                case METHOD_DIRECT_DEBIT:
                case METHOD_ELECTRONIC_INVOICE:
                case METHOD_GIFT_CARD:
                case METHOD_MOBILE_PAYMENT:
                case METHOD_ONLINE_BANK_TRANSFER:
                case METHOD_OPEN_INVOICE:
                case METHOD_PREPAID_CARD:
                case METHOD_TERMINAL:
                case METHOD_WALLET:
                    return this.method;
            }
        }
        return INVALID_VALUE;
    }
}
