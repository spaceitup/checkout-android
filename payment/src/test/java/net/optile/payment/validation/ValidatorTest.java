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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.PaymentMethod;

@RunWith(RobolectricTestRunner.class)
public class ValidatorTest {

    @Test
    public void validateAccountNumber() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.ACCOUNT_NUMBER, "4556260657599841", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentInputType.ACCOUNT_NUMBER, "4556260657599841", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentInputType.ACCOUNT_NUMBER, null, null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.MOBILE_PAYMENT, PaymentInputType.ACCOUNT_NUMBER, "12345", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.MOBILE_PAYMENT, PaymentInputType.ACCOUNT_NUMBER, "", null);
        assertTrue(result.isError());
    }

    @Test
    public void validateHolderName() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.HOLDER_NAME, "John Doe", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.HOLDER_NAME, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.DEBIT_CARD, PaymentInputType.HOLDER_NAME, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryDate() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_DATE, "04", "2030");
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_DATE, "04", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_DATE, null, "2020");
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_DATE, "03", "2017");
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryMonth() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, "01", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, "13", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, "January", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateExpiryYear() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_YEAR, "2018", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, "18", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, "abc", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.EXPIRY_MONTH, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateVerificationCode() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.VERIFICATION_CODE, "123", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.VERIFICATION_CODE, "4321", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.VERIFICATION_CODE, "12345", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.VERIFICATION_CODE, "12a", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.VERIFICATION_CODE, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateBankCode() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.BANK_CODE, "abcd", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.BANK_CODE, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.CREDIT_CARD, PaymentInputType.BANK_CODE, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateIban() {
        Validator validator = new Validator();

        ValidationResult result =
            validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.IBAN, "AT022050302101023600", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.IBAN, "ABCD", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.IBAN, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.IBAN, null, null);
        assertTrue(result.isError());
    }

    @Test
    public void validateBic() {
        Validator validator = new Validator();

        ValidationResult result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.BIC, "AABSDE31XXX", null);
        assertFalse(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.BIC, "ABCD", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.BIC, "", null);
        assertTrue(result.isError());

        result = validator.validate(PaymentMethod.ONLINE_BANK_TRANSFER, PaymentInputType.BIC, null, null);
        assertTrue(result.isError());
    }
}
