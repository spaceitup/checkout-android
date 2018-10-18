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

import net.optile.payment.core.PaymentInputType;
import android.text.TextUtils;

/**
 * Class for validating input type values
 */
public class Validator {

    /** 
     * Validate the given input value defined by its type
     * 
     * @param type the input type 
     * @param value holding the value for the given input type
     */
    public ValidateResult validate(String type, String value) {

        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("validation type may not be null or empty");
        }
        switch (type) {
        case PaymentInputType.ACCOUNT_NUMBER:
            return validateAccountNumber(value);
        case PaymentInputType.HOLDER_NAME:
            return validateHolderName(value);
        case PaymentInputType.EXPIRY_MONTH:
            return validateExpiryMonth(value);
        case PaymentInputType.EXPIRY_YEAR:
            return validateExpiryYear(value);
        case PaymentInputType.VERIFICATION_CODE:
            return validateVerificationCode(value);
        case PaymentInputType.BANK_CODE:
            return validateBankCode(value);
        case PaymentInputType.IBAN:
            return validateIban(value);
        case PaymentInputType.BIC:
            return validateBic(value);
        default:
            throw new IllegalArgumentException("Invalid validation type: " + type);
        }
    }

    public boolean supportsType(String type) {
        return PaymentInputType.isValid(type);
    }
    
    private ValidateResult validateAccountNumber(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_ACCOUNT_NUMBER;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateHolderName(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_HOLDER_NAME;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateExpiryMonth(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_EXPIRY_MONTH;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateExpiryYear(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_EXPIRY_YEAR;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateVerificationCode(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_VERIFICATION_CODE;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateBankCode(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_BANK_CODE;
        }
        return new ValidateResult(error);
    }

    private ValidateResult validateIban(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_IBAN;
        }
        return new ValidateResult(error);
    }
    
    private ValidateResult validateBic(String value) {
        String error = null;
        
        if (TextUtils.isEmpty(value)) {
            error = ValidateResult.MISSING_BIC;
        }
        return new ValidateResult(error);
    }
}
