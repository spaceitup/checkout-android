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

public class WidgetParametersTest {

    @Test
    public void createBuilder() {
        WidgetParameters.Builder builder = WidgetParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        WidgetParameters params = WidgetParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getTextInputTheme(), R.style.PaymentThemeTextInput);
        assertEquals(params.getButtonTheme(), R.style.PaymentThemeButton);
        assertEquals(params.getButtonLabelStyle(), R.style.PaymentText_Medium_Bold_Light);

        assertEquals(params.getCheckBoxTheme(), R.style.PaymentThemeCheckBox);
        assertEquals(params.getCheckBoxLabelCheckedStyle(), R.style.PaymentText_Medium);
        assertEquals(params.getCheckBoxLabelUncheckedStyle(), R.style.PaymentText_Medium_Hint);
        assertEquals(params.getSelectLabelStyle(), R.style.PaymentText_Tiny);

        assertEquals(params.getValidationColorOk(), R.color.pmvalidation_ok);
        assertEquals(params.getValidationColorUnknown(), R.color.pmvalidation_unknown);
        assertEquals(params.getValidationColorError(), R.color.pmvalidation_error);

        assertEquals(params.getHintDrawable(), R.drawable.ic_hint);
        assertEquals(params.getInfoLabelStyle(), R.style.PaymentText_Small);

        assertEquals(params.getInputTypeIcon(PaymentInputType.HOLDER_NAME), R.drawable.ic_name);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_DATE), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_MONTH), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_YEAR), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.BANK_CODE), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.ACCOUNT_NUMBER), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.IBAN), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.BIC), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.VERIFICATION_CODE), R.drawable.ic_lock);
        assertEquals(params.getInputTypeIcon("default"), R.drawable.ic_default);
    }

    @Test
    public void getTextInputTheme() {
        int value = R.style.PaymentThemeTextInput;
        WidgetParameters params = WidgetParameters.createBuilder().
            setTextInputTheme(value).build();
        assertEquals(params.getTextInputTheme(), value);
    }

    @Test
    public void getButtonTheme() {
        int value = R.style.PaymentThemeButton;
        WidgetParameters params = WidgetParameters.createBuilder().
            setButtonTheme(value).build();
        assertEquals(params.getButtonTheme(), value);
    }

    @Test
    public void getButtonLabelStyle() {
        int value = R.style.PaymentText_Medium_Bold_Light;
        WidgetParameters params = WidgetParameters.createBuilder().
            setButtonLabelStyle(value).build();
        assertEquals(params.getButtonLabelStyle(), value);
    }

    @Test
    public void getCheckBoxTheme() {
        int value = R.style.PaymentThemeCheckBox;
        WidgetParameters params = WidgetParameters.createBuilder().
            setCheckBoxTheme(value).build();
        assertEquals(params.getCheckBoxTheme(), value);
    }

    @Test
    public void getCheckBoxLabelCheckedStyle() {
        int value = R.style.PaymentText_Medium;
        WidgetParameters params = WidgetParameters.createBuilder().
            setCheckBoxLabelCheckedStyle(value).build();
        assertEquals(params.getCheckBoxLabelCheckedStyle(), value);
    }

    @Test
    public void getCheckBoxLabelUncheckedStyle() {
        int value = R.style.PaymentText_Medium_Hint;
        WidgetParameters params = WidgetParameters.createBuilder().
            setCheckBoxLabelUncheckedStyle(value).build();
        assertEquals(params.getCheckBoxLabelUncheckedStyle(), value);
    }

    @Test
    public void getSelectLabelStyle() {
        int value = R.style.PaymentText_Tiny;
        WidgetParameters params = WidgetParameters.createBuilder().
            setSelectLabelStyle(value).build();
        assertEquals(params.getSelectLabelStyle(), value);
    }

    @Test
    public void getInputTypeIcon() {
        int value = R.drawable.ic_card;
        String name = "test";
        WidgetParameters params = WidgetParameters.createBuilder().
            putInputTypeIcon(name, value).build();
        assertEquals(params.getInputTypeIcon(name), value);
    }

    @Test
    public void getValidationColorOk() {
        int value = R.color.pmvalidation_ok;
        WidgetParameters params = WidgetParameters.createBuilder().
            setValidationColorOk(value).build();
        assertEquals(params.getValidationColorOk(), value);
    }

    @Test
    public void getValidationColorUnknown() {
        int value = R.color.pmvalidation_unknown;
        WidgetParameters params = WidgetParameters.createBuilder().
            setValidationColorUnknown(value).build();
        assertEquals(params.getValidationColorUnknown(), value);
    }

    @Test
    public void getValidationColorError() {
        int value = R.color.pmvalidation_error;
        WidgetParameters params = WidgetParameters.createBuilder().
            setValidationColorError(value).build();
        assertEquals(params.getValidationColorError(), value);
    }

    @Test
    public void getHintDrawable() {
        int value = R.drawable.ic_hint;
        WidgetParameters params = WidgetParameters.createBuilder().
            setHintDrawable(value).build();
        assertEquals(params.getHintDrawable(), value);
    }

    @Test
    public void getInfoLabelStyle() {
        int value = R.style.PaymentText_Small;
        WidgetParameters params = WidgetParameters.createBuilder().
            setInfoLabelStyle(value).build();
        assertEquals(params.getInfoLabelStyle(), value);
    }
}
