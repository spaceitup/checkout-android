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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.R;
import androidx.test.core.app.ApplicationProvider;


@RunWith(RobolectricTestRunner.class)
public class ValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_invalidContext() {
        Validator.createInstance(null, R.raw.validations);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_invalidResource() {
        Validator.createInstance(ApplicationProvider.getApplicationContext(), 0);
    }

    @Test
    public void createInstance_success() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        assertNotNull(validator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidMethod() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        validator.validate(null, PaymentMethodCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "4111111111111111", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidCode() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        validator.validate(PaymentMethod.CREDIT_CARD, null, PaymentInputType.ACCOUNT_NUMBER, "4111111111111111", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidType() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, null, "4111111111111111", null);
    }

    @Test
    public void validate_accountNumber_defaultValidation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;

        result = validator.validate("method", "code", PaymentInputType.ACCOUNT_NUMBER, "12345", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.ACCOUNT_NUMBER, "12345abcd", null);
        assertTrue(result.isError());
        
        result = validator.validate("method", "code", PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.ACCOUNT_NUMBER, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_cardNumber_defaultValidation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;

        result = validator.validate(PaymentMethod.CREDIT_CARD, "code", PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, "code", PaymentInputType.ACCOUNT_NUMBER, "12345", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, "code", PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, "code", PaymentInputType.ACCOUNT_NUMBER, null, null);
        assertTrue(result.isError());
    }
    
    @Test
    public void validate_VerificationCode_defaultValidation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.VERIFICATION_CODE, "123456789", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
        
        result = validator.validate("method", "code", PaymentInputType.VERIFICATION_CODE, null, null);
        assertFalse(result.isError());
    }

    @Test
    public void validate_CREDIT_AMEX_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.ACCOUNT_NUMBER, "373051954985299", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.VERIFICATION_CODE, "123a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.AMEX, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_CASTORAMA_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.ACCOUNT_NUMBER, "4111111111111111", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.VERIFICATION_CODE, "123a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.CASTORAMA, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_DINERS_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.ACCOUNT_NUMBER, "30282713214300", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DINERS, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_DISCOVER_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.ACCOUNT_NUMBER, "6011548597185331", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.DISCOVER, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_MASTERCARD_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.ACCOUNT_NUMBER, "5290836048016633", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.MASTERCARD, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_UNIONPAY_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.ACCOUNT_NUMBER, "62123456789000003", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.UNIONPAY, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }
    
    @Test
    public void validate_CREDIT_VISA_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "4556260657599841", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_CREDIT_VISA_DANKORT_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.ACCOUNT_NUMBER, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentMethodCodes.VISA_DANKORT, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_DEBIT_VISAELECTRON_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.ACCOUNT_NUMBER, "4917300800000000", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.VERIFICATION_CODE, "1234", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.VISAELECTRON, PaymentInputType.VERIFICATION_CODE, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validate_DEBIT_CARTEBANCAIRE_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.CARTEBANCAIRE, PaymentInputType.ACCOUNT_NUMBER, "4035501000000008", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.CARTEBANCAIRE, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.CARTEBANCAIRE, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.CARTEBANCAIRE, PaymentInputType.VERIFICATION_CODE, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.CARTEBANCAIRE, PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
    }    

    @Test
    public void validate_DEBIT_MAESTRO_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTRO, PaymentInputType.ACCOUNT_NUMBER, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTRO, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTRO, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTRO, PaymentInputType.VERIFICATION_CODE, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTRO, PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
    }    

    @Test
    public void validate_DEBIT_MAESTROUK_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTROUK, PaymentInputType.ACCOUNT_NUMBER, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTROUK, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTROUK, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTROUK, PaymentInputType.VERIFICATION_CODE, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.MAESTROUK, PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
    }    

    @Test
    public void validate_DEBIT_POSTEPAY_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.POSTEPAY, PaymentInputType.ACCOUNT_NUMBER, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.POSTEPAY, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.POSTEPAY, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.POSTEPAY, PaymentInputType.VERIFICATION_CODE, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.POSTEPAY, PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
    }    

    @Test
    public void validate_DEBIT_SOLO_validation() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.SOLO, PaymentInputType.ACCOUNT_NUMBER, "6759649826438453", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.SOLO, PaymentInputType.ACCOUNT_NUMBER, "36699", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.SOLO, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
        
        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.SOLO, PaymentInputType.VERIFICATION_CODE, "123456", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentMethodCodes.SOLO, PaymentInputType.VERIFICATION_CODE, "", null);
        assertFalse(result.isError());
    }        
    
    @Test
    public void validate_HolderName() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);

        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.HOLDER_NAME, "John Doe", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.HOLDER_NAME, "", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.HOLDER_NAME, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryDate() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);

        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.EXPIRY_DATE, "04", "2030");
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_DATE, "04", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_DATE, null, "2020");
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_DATE, "03", "2017");
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryMonth() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);

        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, "01", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, "13", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, "January", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryYear() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);

        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.EXPIRY_YEAR, "2018", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, "18", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, "abc", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.EXPIRY_MONTH, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateBankCode() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.BANK_CODE, "abcd", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.BANK_CODE, "", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.BANK_CODE, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateIban() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.IBAN, "AT022050302101023600", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.IBAN, "ABCD", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.IBAN, "", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.IBAN, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateBic() {
        Validator validator = Validator.createInstance(ApplicationProvider.getApplicationContext(), R.raw.validations);
        ValidationResult result;
        result = validator.validate("method", "code", PaymentInputType.BIC, "AABSDE31XXX", null);
        assertFalse(result.isError());

        result = validator.validate("method", "code", PaymentInputType.BIC, "ABCD", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.BIC, "", null);
        assertTrue(result.isError());

        result = validator.validate("method", "code", PaymentInputType.BIC, null, null);
        assertTrue(result.isError());
    }
}
