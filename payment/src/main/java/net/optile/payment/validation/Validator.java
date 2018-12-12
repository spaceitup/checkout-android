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

import java.io.IOException;
import java.util.Calendar;

import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.util.GsonHelper;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for validating input type values
 */
public class Validator {

    public final static String REGEX_MONTH = "(^0[1-9]|1[0-2]$)";
    public final static String REGEX_YEAR = "^(20)\\d{2}$";
    public final static String REGEX_BIC = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)";
    public final static String REGEX_ACCOUNT_NUMBER = "^[0-9]+$";
    public final static String REGEX_VERIFICATION_CODE = "^[0-9]*$";
    private final static String TAG = "pay_Validator";
    private final Validations validations;

    /**
     * Construct a new Validator with the provided validations
     *
     * @param validations the list of validations to be used to validate input values
     */
    private Validator(Validations validations) {
        this.validations = validations;
    }

    /**
     * Construct a new default Validator which will be used to validate the entered input values from the user.
     *
     * @param context needed to construct the default Validator
     * @param validationResId the raw json resource containing validations to be used
     * @return the newly created Validator
     */
    public final static Validator createInstance(Context context, int validationResId) {
        if (context == null) {
            throw new IllegalArgumentException("Context may not be null");
        }
        try {
            String val = PaymentUtils.readRawResource(context.getResources(), validationResId);
            return new Validator(GsonHelper.getInstance().fromJson(val, Validations.class));
        } catch (IOException e) {
            Log.wtf(TAG, e);
        } catch (JsonSyntaxException e) {
            Log.wtf(TAG, e);
        }
        throw new IllegalArgumentException("Error loading validations resource file, make sure it exist and is valid json.");
    }

    /**
     * Get the validation for the given method, code and type.
     *
     * @param method Payment method like CREDIT_CARD
     * @param code Payment code like VISA
     * @param type payment input type like "number"
     * @return Validation or null if not found
     */
    public Validation getValidation(String method, String code, String type) {
        return validations != null ? validations.get(method, code, type) : null;
    }

    /**
     * Validate the given input values defined by its type
     *
     * @param method the Payment method like CREDIT_CARD
     * @param code the payment code like VISA
     * @param type the PaymentInputType like "number"
     * @param value1 holding the mandatory first value for the given input type, may be empty
     * @param value2 holding the optional second value for the given input type
     */
    public ValidationResult validate(String method, String code, String type, String value1, String value2) {

        if (TextUtils.isEmpty(method)) {
            throw new IllegalArgumentException("validate method may not be null or empty");
        }
        if (TextUtils.isEmpty(code)) {
            throw new IllegalArgumentException("validate code may not be null or empty");
        }
        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("validate type may not be null or empty");
        }
        value1 = value1 == null ? "" : value1;
        value2 = value2 == null ? "" : value2;
        Validation val = validations.get(method, code, type);

        switch (type) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return validateAccountNumber(method, value1, val);
            case PaymentInputType.VERIFICATION_CODE:
                return validateVerificationCode(value1, val);
            case PaymentInputType.HOLDER_NAME:
                return validateHolderName(value1);
            case PaymentInputType.EXPIRY_DATE:
                return validateExpiryDate(value1, value2);
            case PaymentInputType.EXPIRY_MONTH:
                return validateExpiryMonth(value1);
            case PaymentInputType.EXPIRY_YEAR:
                return validateExpiryYear(value1);
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

    private ValidationResult validateAccountNumber(String method, String number, Validation val) {

        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                return validateCardNumber(number, val);
            default:
                if (!number.matches(REGEX_ACCOUNT_NUMBER)) {
                    if (TextUtils.isEmpty(number)) {
                        return new ValidationResult(ValidationResult.MISSING_ACCOUNT_NUMBER);
                    }
                    return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
                }
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateCardNumber(String number, Validation val) {
        String regex = val != null ? val.getRegex() : REGEX_ACCOUNT_NUMBER;

        if (!number.matches(regex)) {
            if (TextUtils.isEmpty(number)) {
                return new ValidationResult(ValidationResult.MISSING_ACCOUNT_NUMBER);
            }
            return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
        }
        if (!CardNumberValidator.isValidLuhn(number)) {
            return new ValidationResult(ValidationResult.INVALID_ACCOUNT_NUMBER);
        }
        return new ValidationResult(null);
    }

    private ValidationResult validateVerificationCode(String verificationCode, Validation val) {
        String regex = val != null ? val.getRegex() : REGEX_VERIFICATION_CODE;

        if (!verificationCode.matches(regex)) {
            if (TextUtils.isEmpty(verificationCode)) {
                return new ValidationResult(ValidationResult.MISSING_VERIFICATION_CODE);
            }
            return new ValidationResult(ValidationResult.INVALID_VERIFICATION_CODE);
        }
        return new ValidationResult(null);
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
