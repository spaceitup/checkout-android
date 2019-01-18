/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.resource;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.res.Resources;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;

@RunWith(RobolectricTestRunner.class)
public class ResourceLoaderTest {

    @Test(expected = PaymentException.class)
    public void loadPaymentGroups_invalidResourceId() throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        ResourceLoader.loadPaymentGroups(res, 0);
    }

    @Test
    public void loadPaymentGroups_success() throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        Map<String, PaymentGroup> groups = ResourceLoader.loadPaymentGroups(res, R.raw.groups);
        assertNotNull(groups.get("MASTERCARD"));
        assertNotNull(groups.get("VISA"));
        assertNotNull(groups.get("AMEX"));
    }

    @Test(expected = PaymentException.class)
    public void loadValidations_invalidResourceId() throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        ResourceLoader.loadValidations(res, 0);
    }

    @Test
    public void loadValidations_success() throws PaymentException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        Map<String, ValidationGroup> validations = ResourceLoader.loadValidations(res, R.raw.validations);
        validateGroup(validations, "AMEX");
        validateGroup(validations, "CASTORAMA");
        validateGroup(validations, "DINERS");
        validateGroup(validations, "DISCOVER");
        validateGroup(validations, "MASTERCARD");
        validateGroup(validations, "UNIONPAY");
        validateGroup(validations, "VISA");
        validateGroup(validations, "VISA_DANKORT");
        validateGroup(validations, "VISAELECTRON");
        validateGroup(validations, "CARTEBANCAIRE");
        validateGroup(validations, "CARTEBLEUE");
        validateGroup(validations, "MAESTRO");
        validateGroup(validations, "MAESTROUK");
        validateGroup(validations, "POSTEPAY");
        validateGroup(validations, "SOLO");
    }

    @Test(expected = IOException.class)
    public void readRawResource_invalidResourceId() throws IOException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        ResourceLoader.readRawResource(res, 0);
    }

    @Test
    public void readRawResource_defaultValidation() throws IOException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        String str = ResourceLoader.readRawResource(res, R.raw.validations);
        assertNotNull(str);
    }

    @Test
    public void readRawResource_defaultGroups() throws IOException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        String str = ResourceLoader.readRawResource(res, R.raw.groups);
        assertNotNull(str);
    }

    private void validateGroup(Map<String, ValidationGroup> validations, String name) {
        ValidationGroup group = validations.get(name);
        assertNotNull(group);
        assertNotNull(group.getValidationRegex("number"));
        assertNotNull(group.getValidationRegex("verificationCode"));
    }
}