/**
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
 * This class is designed to hold interaction information that prescribes further reaction of merchant portal to this transaction or
 * operation.
 */
public class Interaction {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ CODE_PROCEED,
            CODE_ABORT,
            CODE_TRY_OTHER_NETWORK,
            CODE_TRY_OTHER_ACCOUNT,
            CODE_RETRY,
            CODE_RELOAD,
            CODE_UNKNOWN })
            public @interface InteractionCode {}

    public final static String CODE_PROCEED             = "CODE_PROCEED";
    public final static String CODE_ABORT               = "CODE_ABORT";
    public final static String CODE_TRY_OTHER_NETWORK   = "CODE_TRY_OTHER_NETWORK";
    public final static String CODE_TRY_OTHER_ACCOUNT   = "CODE_TRY_OTHER_ACCOUNT";
    public final static String CODE_RETRY               = "CODE_RETRY";
    public final static String CODE_RELOAD              = "CODE_RELOAD";
    public final static String CODE_UNKNOWN             = "UnknownCode";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ REASON_OK,
            REASON_PENDING,
            REASON_STRONG_AUTHENTICATION,
            REASON_DECLINED,
            REASON_EXCEEDS_LIMIT,
            REASON_TEMPORARY_FAILURE,
            REASON_NETWORK_FAILURE,
            REASON_BLACKLISTED,
            REASON_BLOCKED,
            REASON_SYSTEM_FAILURE,
            REASON_INVALID_ACCOUNT,
            REASON_FRAUD,
            REASON_ADDITIONAL_NETWORKS,
            REASON_INVALID_REQUEST,
            REASON_SCHEDULED,
            REASON_NO_NETWORKS,
            REASON_DUPLICATE_OPERATION,
            REASON_CHARGEBACK,
            REASON_RISK_DETECTED,
            REASON_CUSTOMER_ABORT,
            REASON_EXPIRED_SESSION,
            REASON_EXPIRED_ACCOUNT,
            REASON_ACCOUNT_NOT_ACTIVATED,
            REASON_TRUSTED_CUSTOMER,
            REASON_UNKNOWN_CUSTOMER,
            REASON_ACTIVATED,
            REASON_UPDATED,
            REASON_TAKE_ACTION,
            REASON_UNKNOWN })
            public @interface InteractionReason {}

    public final static String REASON_OK                       = "OK";
    public final static String REASON_PENDING                  = "PENDING";
    public final static String REASON_STRONG_AUTHENTICATION    = "STRONG_AUTHENTICATION";
    public final static String REASON_DECLINED                 = "DECLINED";
    public final static String REASON_EXCEEDS_LIMIT            = "EXCEEDS_LIMIT";
    public final static String REASON_TEMPORARY_FAILURE        = "TEMPORARY_FAILURE";
    public final static String REASON_NETWORK_FAILURE          = "NETWORK_FAILURE";
    public final static String REASON_BLACKLISTED              = "BLACKLISTED";
    public final static String REASON_BLOCKED                  = "BLOCKED";
    public final static String REASON_SYSTEM_FAILURE           = "SYSTEM_FAILURE";
    public final static String REASON_INVALID_ACCOUNT          = "INVALID_ACCOUNT";
    public final static String REASON_FRAUD                    = "FRAUD";
    public final static String REASON_ADDITIONAL_NETWORKS      = "ADDITIONAL_NETWORKS";
    public final static String REASON_INVALID_REQUEST          = "INVALID_REQUEST";
    public final static String REASON_SCHEDULED                = "SCHEDULED";
    public final static String REASON_NO_NETWORKS              = "NO_NETWORKS";
    public final static String REASON_DUPLICATE_OPERATION      = "DUPLICATE_OPERATION";
    public final static String REASON_CHARGEBACK               = "CHARGEBACK";
    public final static String REASON_RISK_DETECTED            = "RISK_DETECTED";
    public final static String REASON_CUSTOMER_ABORT           = "CUSTOMER_ABORT";
    public final static String REASON_EXPIRED_SESSION          = "EXPIRED_SESSION";
    public final static String REASON_EXPIRED_ACCOUNT          = "EXPIRED_ACCOUNT";
    public final static String REASON_ACCOUNT_NOT_ACTIVATED    = "ACCOUNT_NOT_ACTIVATED";
    public final static String REASON_TRUSTED_CUSTOMER         = "TRUSTED_CUSTOMER";
    public final static String REASON_UNKNOWN_CUSTOMER         = "UNKNOWN_CUSTOMER";
    public final static String REASON_ACTIVATED                = "ACTIVATED";
    public final static String REASON_UPDATED                  = "UPDATED";
    public final static String REASON_TAKE_ACTION              = "TAKE_ACTION";
    public final static String REASON_UNKNOWN                  = "UnknownReason";

    /** Simple API, always present */
    private String code;
    /** Simple API, always present */
    private String reason;

    /**
     * Gets value of code.
     *
     * @return the code.
     */
    @InteractionCode
    public String getCode() {
        return code;
    }

    /**
     * Sets value of code.
     *
     * @param code the code to set.
     */
    public void setCode(@InteractionCode String code) {
        this.code = code;
    }

    /**
     * Gets value of reason.
     *
     * @return the reason.
     */
    @InteractionReason
    public String getReason() {
        return reason;
    }

    /**
     * Sets value of reason.
     *
     * @param reason the reason to set.
     */
    public void setReason(@InteractionReason String reason) {
        this.reason = reason;
    }

    /**
     * Gets code as a checked value.
     * If the value does not match any predefined modes then return
     * CODE_UNKNOWN.
     *
     * @return the code
     */
    @InteractionCode
    public String getCheckedCode() {

        if (this.code != null) {
            switch (this.code) {
            case CODE_PROCEED:
            case CODE_ABORT:
            case CODE_TRY_OTHER_NETWORK:
            case CODE_TRY_OTHER_ACCOUNT:
            case CODE_RETRY:
            case CODE_RELOAD:
                return this.code;
            }
        }
        return CODE_UNKNOWN;
    }

    /**
     * Gets reason as a checked value.
     * If the value does not match any predefined modes then return
     * REASON_UNKNOWN.
     *
     * @return the reason
     */
    @InteractionReason
    public String getCheckedReason() {

        if (this.reason != null) {
            switch (this.reason) {
            case REASON_OK:
            case REASON_PENDING:
            case REASON_STRONG_AUTHENTICATION:
            case REASON_DECLINED:
            case REASON_EXCEEDS_LIMIT:
            case REASON_TEMPORARY_FAILURE:
            case REASON_NETWORK_FAILURE:
            case REASON_BLACKLISTED:
            case REASON_BLOCKED:
            case REASON_SYSTEM_FAILURE:
            case REASON_INVALID_ACCOUNT:
            case REASON_FRAUD:
            case REASON_ADDITIONAL_NETWORKS:
            case REASON_INVALID_REQUEST:
            case REASON_SCHEDULED:
            case REASON_NO_NETWORKS:
            case REASON_DUPLICATE_OPERATION:
            case REASON_CHARGEBACK:
            case REASON_RISK_DETECTED:
            case REASON_CUSTOMER_ABORT:
            case REASON_EXPIRED_SESSION:
            case REASON_EXPIRED_ACCOUNT:
            case REASON_ACCOUNT_NOT_ACTIVATED:
            case REASON_TRUSTED_CUSTOMER:
            case REASON_UNKNOWN_CUSTOMER:
            case REASON_ACTIVATED:
            case REASON_UPDATED:
            case REASON_TAKE_ACTION:
                return this.reason;
            }
        }
        return REASON_UNKNOWN;
    }
}
