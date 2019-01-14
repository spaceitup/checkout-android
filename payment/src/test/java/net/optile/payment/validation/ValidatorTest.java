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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.res.Resources;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.resource.ResourceLoader;


@RunWith(RobolectricTestRunner.class)
public class ValidatorTest {

    private Validator validator;

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_invalidContext() {
        new Validator(null);
    }

    @Test(expected = PaymentException.class)
    public void createInstance_invalidResource() throws PaymentException {
        createValidator(0);
    }

    @Test
    public void createInstance_success() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        assertNotNull(validator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidMethod() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        validator.validate(null, PaymentMethodCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "4111111111111111", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidType() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, null, "4111111111111111", null);
    }

    @Test
    public void validate_accountNumber_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "12345", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12345ABC", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_cardNumber_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = "code";
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "36699", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12345", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_verificationCode_missingValidation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456789", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_CREDIT_AMEX_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.AMEX;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "373051954985299", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_AMEX_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.AMEX;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "123", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_CASTORAMA_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.CASTORAMA;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4111111111111111", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_CASTORAMA_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.CASTORAMA;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "123", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_DINERS_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.DINERS;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "30282713214300", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_DINERS_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.DINERS;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_DISCOVER_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.DISCOVER;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6011548597185331", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_DISCOVER_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.DISCOVER;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_MASTERCARD_validation() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.MASTERCARD;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "5290836048016633", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_MASTERCARD_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.MASTERCARD;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }


    @Test
    public void validate_CREDIT_UNIONPAY_accountNumber() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.UNIONPAY;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "62123456789000003", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_UNIONPAY_verificationCode() throws PaymentException {
        final Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.UNIONPAY;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.VISA;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4556260657599841", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.VISA;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_DANKORT_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.VISA_DANKORT;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_CREDIT_VISA_DANKORT_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.CREDIT_CARD;
        final String code = PaymentMethodCodes.VISA_DANKORT;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_DEBIT_VISAELECTRON_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.VISAELECTRON;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_VISAELECTRON_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.VISAELECTRON;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "12a", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "1234", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_VERIFICATION_CODE, result.getError());
    }

    @Test
    public void validate_DEBIT_CARTEBANCAIRE_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.CARTEBANCAIRE;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "4035501000000008", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_CARTEBANCAIRE_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.CARTEBANCAIRE;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_DEBIT_MAESTRO_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.MAESTRO;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_MAESTRO_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.MAESTRO;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_DEBIT_MAESTROUK_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.MAESTROUK;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_MAESTROUK_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.MAESTROUK;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }


    @Test
    public void validate_DEBIT_POSTEPAY_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.POSTEPAY;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_POSTEPAY_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.POSTEPAY;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_DEBIT_SOLO_accountNumber() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.SOLO;
        final String type = PaymentInputType.ACCOUNT_NUMBER;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "36699", null);
        assertEquals(ValidationResult.INVALID_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_ACCOUNT_NUMBER, result.getError());
    }

    @Test
    public void validate_DEBIT_SOLO_verificationCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = PaymentMethod.DEBIT_CARD;
        final String code = PaymentMethodCodes.SOLO;
        final String type = PaymentInputType.VERIFICATION_CODE;

        assertNotNull(validator.getValidationRegex(code, type));

        ValidationResult result;
        result = validator.validate(method, code, type, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "123ABC", null);
        assertEquals(ValidationResult.INVALID_VERIFICATION_CODE, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, null, null);
        assertFalse(result.isError());
    }


    @Test
    public void validate_HolderName() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.HOLDER_NAME;

        ValidationResult result;
        result = validator.validate(method, code, type, "John Doe", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_HOLDER_NAME, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_HOLDER_NAME, result.getError());
    }

    @Test
    public void validateExpiryDate() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_DATE;

        ValidationResult result;
        result = validator.validate(method, code, type, "04", "2030");
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "3", "2017");
        assertEquals(ValidationResult.INVALID_EXPIRY_DATE, result.getError());

        result = validator.validate(method, code, type, "04", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_DATE, result.getError());

        result = validator.validate(method, code, type, null, "2020");
        assertEquals(ValidationResult.MISSING_EXPIRY_DATE, result.getError());
    }

    @Test
    public void validateExpiryMonth() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_MONTH;

        ValidationResult result;
        result = validator.validate(method, code, type, "01", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "13", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, "January", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_MONTH, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_EXPIRY_MONTH, result.getError());

    }

    @Test
    public void validateExpiryYear() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.EXPIRY_YEAR;

        ValidationResult result;
        result = validator.validate(method, code, type, "2018", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "18", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, "abc", null);
        assertEquals(ValidationResult.INVALID_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_EXPIRY_YEAR, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_EXPIRY_YEAR, result.getError());
    }

    @Test
    public void validateBankCode() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.BANK_CODE;

        ValidationResult result;
        result = validator.validate(method, code, type, "abcd", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_BANK_CODE, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_BANK_CODE, result.getError());
    }

    @Test
    public void validateIban() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.IBAN;

        ValidationResult result;
        result = validator.validate(method, code, type, "AT022050302101023600", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "ABCD", null);
        assertEquals(ValidationResult.INVALID_IBAN, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_IBAN, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_IBAN, result.getError());
    }

    @Test
    public void validateBic() throws PaymentException {
        Validator validator = createValidator(R.raw.validations);
        final String method = "method";
        final String code = "code";
        final String type = PaymentInputType.BIC;

        ValidationResult result;
        result = validator.validate(method, code, type, "AABSDE31XXX", null);
        assertFalse(result.isError());

        result = validator.validate(method, code, type, "ABCD", null);
        assertEquals(ValidationResult.INVALID_BIC, result.getError());

        result = validator.validate(method, code, type, "", null);
        assertEquals(ValidationResult.MISSING_BIC, result.getError());

        result = validator.validate(method, code, type, null, null);
        assertEquals(ValidationResult.MISSING_BIC, result.getError());
    }

    private Validator createValidator(int resId) throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        return new Validator(ResourceLoader.loadValidations(res, resId));
    }
}
