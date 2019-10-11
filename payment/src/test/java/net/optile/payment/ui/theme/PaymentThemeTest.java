/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.PaymentTheme;

public class PaymentThemeTest {

    @Test
    public void createBuilder() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        PaymentTheme theme = PaymentTheme.createDefault();
        assertNotNull(theme);

        assertEquals(theme.getInputTypeIcon(PaymentInputType.HOLDER_NAME), R.drawable.ic_name);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.EXPIRY_DATE), R.drawable.ic_date);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.EXPIRY_MONTH), R.drawable.ic_date);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.EXPIRY_YEAR), R.drawable.ic_date);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.BANK_CODE), R.drawable.ic_card);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.ACCOUNT_NUMBER), R.drawable.ic_card);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.IBAN), R.drawable.ic_card);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.BIC), R.drawable.ic_card);
        assertEquals(theme.getInputTypeIcon(PaymentInputType.VERIFICATION_CODE), R.drawable.ic_lock);
        assertEquals(theme.getInputTypeIcon("default"), R.drawable.ic_default);

        assertEquals(theme.getMessageDialogTheme(), R.style.PaymentDialogTheme_Message);
        assertEquals(theme.getDateDialogTheme(), R.style.PaymentDialogTheme_Date);
        assertEquals(theme.getPaymentListTheme(), R.style.PaymentTheme_PaymentList);
        assertEquals(theme.getChargePaymentTheme(), R.style.PaymentTheme_ChargePayment);

        assertEquals(theme.getValidationColorOk(), R.color.pmvalidation_ok);
        assertEquals(theme.getValidationColorUnknown(), R.color.pmvalidation_unknown);
        assertEquals(theme.getValidationColorError(), R.color.pmvalidation_error);
    }

    @Test
    public void getMessageDialogTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setMessageDialogTheme(value).build();
        assertEquals(theme.getMessageDialogTheme(), value);
    }

    @Test
    public void getDateDialogTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setDateDialogTheme(value).build();
        assertEquals(theme.getDateDialogTheme(), value);
    }

    @Test
    public void getPaymentListTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setPaymentListTheme(value).build();
        assertEquals(theme.getPaymentListTheme(), value);
    }

    @Test
    public void getChargePaymentTheme() {
        int value = R.style.PaymentTheme;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setChargePaymentTheme(value).build();
        assertEquals(theme.getChargePaymentTheme(), value);
    }

    @Test
    public void getValidationColorOk() {
        int value = R.color.pmcolor_primary;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setValidationColorOk(value).build();
        assertEquals(theme.getValidationColorOk(), value);
    }

    @Test
    public void getValidationColorUnknown() {
        int value = R.color.pmcolor_primary;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setValidationColorUnknown(value).build();
        assertEquals(theme.getValidationColorUnknown(), value);
    }

    @Test
    public void getValidationColorError() {
        int value = R.color.pmcolor_primary;
        PaymentTheme theme = PaymentTheme.createBuilder().
            setValidationColorError(value).build();
        assertEquals(theme.getValidationColorError(), value);
    }
}
