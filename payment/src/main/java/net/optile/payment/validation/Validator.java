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

import java.util.Calendar;

import android.text.TextUtils;
import android.util.Log;
import android.content.Context;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.PaymentMethod;

/**
 * Class for validating input type values
 */
public class Validator {

    public final static String REGEX_MONTH = "(^0[1-9]|1[0-2]$)";
    public final static String REGEX_YEAR = "^(20)\\d{2}$";
    public final static String REGEX_BIC = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)";
    public final static String REGEX_VERIFICATION_CODE = "^[0-9]{3,4}$";

    private final Settings settings;
    
    public Validator(Settings settings) {
        this.settings = settings;
    }
    
    public final static Validator createDefault(Context context) {
        return new Validator(new Settings());
    }
    
    /**
     * Validate the given input values defined by its type
     *
     * @param method the Payment method
     * @param type the input type
     * @param value1 holding the mandatory first value for the given input type
     * @param value2 holding the optional second value for the given input type
     */
    public ValidationResult validate(String method, String type, String value1, String value2) {

        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("validation type may not be null or empty");
        }
        switch (type) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return validateAccountNumber(method, value1);
            case PaymentInputType.HOLDER_NAME:
                return validateHolderName(value1);
            case PaymentInputType.EXPIRY_DATE:
                return validateExpiryDate(value1, value2);
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

    private ValidationResult validateAccountNumber(String method, String accountNumber) {

        if (TextUtils.isEmpty(accountNumber)) {
            return new ValidationResult(ValidationResult.MISSING_ACCOUNT_NUMBER);
        }
        String error = null;
        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:

                if (!CardNumberValidator.isValidLuhn(accountNumber)) {
                    error = ValidationResult.INVALID_ACCOUNT_NUMBER;
                }
                break;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateHolderName(String holderName) {
        String error = null;

        if (TextUtils.isEmpty(holderName)) {
            error = ValidationResult.MISSING_HOLDER_NAME;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryDate(String month, String year) {
        String error = null;

        if (TextUtils.isEmpty(month) || TextUtils.isEmpty(year)) {
            error = ValidationResult.MISSING_EXPIRY_DATE;
        } else if (!isValidExpiryDate(month, year)) {
            error = ValidationResult.INVALID_EXPIRY_DATE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryMonth(String month) {
        String error = null;

        if (TextUtils.isEmpty(month)) {
            error = ValidationResult.MISSING_EXPIRY_MONTH;
        } else if (!month.matches(REGEX_MONTH)) {
            error = ValidationResult.INVALID_EXPIRY_MONTH;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateExpiryYear(String year) {
        String error = null;

        if (TextUtils.isEmpty(year)) {
            error = ValidationResult.MISSING_EXPIRY_YEAR;
        } else if (!year.matches(REGEX_YEAR)) {
            error = ValidationResult.INVALID_EXPIRY_YEAR;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateVerificationCode(String verificationCode) {
        String error = null;

        if (TextUtils.isEmpty(verificationCode)) {
            error = ValidationResult.MISSING_VERIFICATION_CODE;
        } else if (!verificationCode.matches(REGEX_VERIFICATION_CODE)) {
            error = ValidationResult.INVALID_VERIFICATION_CODE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateBankCode(String bankCode) {
        String error = null;

        if (TextUtils.isEmpty(bankCode)) {
            error = ValidationResult.MISSING_BANK_CODE;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateIban(String iban) {
        String error = null;

        if (TextUtils.isEmpty(iban)) {
            error = ValidationResult.MISSING_IBAN;
        } else if (!IbanValidator.isValidIban(iban)) {
            error = ValidationResult.INVALID_IBAN;
        }
        return new ValidationResult(error);
    }

    private ValidationResult validateBic(String bic) {
        String error = null;

        if (TextUtils.isEmpty(bic)) {
            error = ValidationResult.MISSING_BIC;
        } else if (!bic.matches(REGEX_BIC)) {
            error = ValidationResult.INVALID_BIC;
        }
        return new ValidationResult(error);
    }

    private boolean isValidExpiryDate(String month, String year) {

        if (!(month.matches(REGEX_MONTH) && year.matches(REGEX_YEAR))) {
            return false;
        }
        try {
            int expMonth = Integer.parseInt(month);
            int expYear = Integer.parseInt(year);

            Calendar cal = Calendar.getInstance();
            int curMonth = cal.get(Calendar.MONTH) + 1;
            int curYear = cal.get(Calendar.YEAR);

            if (expYear > curYear || (expYear == curYear && expMonth >= curMonth)) {
                return true;
            }
        } catch (NumberFormatException e) {
            // this should never happen since the regex makes sure both are integers
            Log.wtf("pay_DateValidator", e);
        }
        return false;
    }

}
