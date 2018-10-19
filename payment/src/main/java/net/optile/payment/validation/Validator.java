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

package net.optile.payment.validation;

import net.optile.payment.core.PaymentInputType;
import android.text.TextUtils;

/**
 * Class for validating input type values
 */
public class Validator {

    /** 
     * Validate the given input values defined by its type
     * 
     * @param type the input type 
     * @param value1 holding the mandatory first value for the given input type
     * @param value2 holding the optional second value for the given input type
     */
    public ValidationResult validate(String type, String value1, String value2) {

        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("validation type may not be null or empty");
        }
        switch (type) {
        case PaymentInputType.ACCOUNT_NUMBER:
            return validateAccountNumber(value1);
        case PaymentInputType.HOLDER_NAME:
            return validateHolderName(value1);
        case PaymentInputType.EXPIRY_MONTH:
            return validateExpiryMonth(value1);
        case PaymentInputType.EXPIRY_YEAR:
            return validateExpiryYear(value1);
        case PaymentInputType.VERIFICATION_CODE:
            return validateVerificationCode(value1);
        case PaymentInputType.BANK_CODE:
            return validateBankCode(value1);
        case PaymentInputType.IBAN:
            return validateIban(value1);
        case PaymentInputType.BIC:
            return validateBic(value1);
        default:
            return new ValidationResult(null);
        }
    }
    
    private ValidationResult validateAccountNumber(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_ACCOUNT_NUMBER;
        } else if (!CardNumberValidator.isValidLuhn(value)) {
            error = ValidationResult.INVALID_ACCOUNT_NUMBER;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateHolderName(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_HOLDER_NAME;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryMonth(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_EXPIRY_MONTH;
        } 
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryYear(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_EXPIRY_YEAR;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateVerificationCode(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_VERIFICATION_CODE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateBankCode(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_BANK_CODE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateIban(String value) {
        String error = null;

        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_IBAN;
        }
        return new ValidationResult(error);
    }
    
    private ValidationResult validateBic(String value) {
        String error = null;
        
        if (TextUtils.isEmpty(value)) {
            error = ValidationResult.MISSING_BIC;
        }
        return new ValidationResult(error);
    }
}
