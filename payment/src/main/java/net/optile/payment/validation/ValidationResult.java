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

/**
 * Class holding the result of the validation
 */
public class ValidationResult {

    /** The validation error codes */
    public final static String INVALID_ACCOUNT_NUMBER = "INVALID_ACCOUNT_NUMBER";
    public final static String MISSING_ACCOUNT_NUMBER = "MISSING_ACCOUNT_NUMBER";

    public final static String INVALID_HOLDER_NAME = "INVALID_HOLDER_NAME";
    public final static String MISSING_HOLDER_NAME = "MISSING_HOLDER_NAME";

    public final static String INVALID_EXPIRY_DATE = "INVALID_EXPIRY_DATE";
    public final static String MISSING_EXPIRY_DATE = "MISSING_EXPIRY_DATE";

    public final static String INVALID_EXPIRY_MONTH = "INVALID_EXPIRY_MONTH";
    public final static String MISSING_EXPIRY_MONTH = "MISSING_EXPIRY_MONTH";

    public final static String INVALID_EXPIRY_YEAR = "INVALID_EXPIRY_YEAR";
    public final static String MISSING_EXPIRY_YEAR = "MISSING_EXPIRY_YEAR";

    public final static String INVALID_VERIFICATION_CODE = "INVALID_VERIFICATION_CODE";
    public final static String MISSING_VERIFICATION_CODE = "MISSING_VERIFICATION_CODE";

    public final static String INVALID_BANK_CODE = "INVALID_BANK_CODE";
    public final static String MISSING_BANK_CODE = "MISSING_BANK_CODE";

    public final static String INVALID_IBAN = "INVALID_IBAN";
    public final static String MISSING_IBAN = "MISSING_IBAN";

    public final static String INVALID_BIC = "INVALID_BIC";
    public final static String MISSING_BIC = "MISSING_BIC";

    private final String error;

    private String message;

    public ValidationResult(String error) {
        this.error = error;
    }

    public boolean isError() {
        return this.error != null;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

