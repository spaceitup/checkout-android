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

package net.optile.payment.ui.theme;

import java.util.HashMap;
import java.util.Map;

import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

/**
 * Class for holding the WidgetParameters for the PaymentTheme.
 * These parameters may be used to theme the widgets shown inside the payment cards, i.e. the textInput, button and checkbox widgets
 */
public final class WidgetParameters {

    private final Map<String, Integer> iconMapping;
    private final int textInputTheme;
    private final int buttonTheme;
    private final int buttonLabelStyle;
    private final int checkBoxTheme;
    private final int checkBoxLabelCheckedStyle;
    private final int checkBoxLabelUncheckedStyle;
    private final int selectLabelStyle;
    private final int validationColorUnknown;
    private final int validationColorOk;
    private final int validationColorError;

    private WidgetParameters(Builder builder) {
        this.iconMapping = new HashMap<>(builder.iconMapping);
        this.textInputTheme = builder.textInputTheme;
        this.buttonTheme = builder.buttonTheme;
        this.buttonLabelStyle = builder.buttonLabelStyle;
        this.checkBoxTheme = builder.checkBoxTheme;
        this.checkBoxLabelCheckedStyle = builder.checkBoxLabelCheckedStyle;
        this.checkBoxLabelUncheckedStyle = builder.checkBoxLabelUncheckedStyle;
        this.selectLabelStyle = builder.selectLabelStyle;
        this.validationColorOk = builder.validationColorOk;
        this.validationColorUnknown = builder.validationColorUnknown;
        this.validationColorError = builder.validationColorError;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static WidgetParameters createDefault() {
        return createBuilder().
            setTextInputTheme(R.style.PaymentThemeTextInput).
            setButtonTheme(R.style.PaymentThemeButton).
            setButtonLabelStyle(R.style.PaymentText_Medium_Bold_Light).
            setCheckBoxTheme(R.style.PaymentThemeCheckBox).
            setCheckBoxLabelCheckedStyle(R.style.PaymentText_Medium).
            setCheckBoxLabelUncheckedStyle(R.style.PaymentText_Medium_Hint).
            setSelectLabelStyle(R.style.PaymentText_Tiny).
            setDefaultIconMapping().
            setValidationColorOk(R.color.pmvalidation_ok).
            setValidationColorUnknown(R.color.pmvalidation_unknown).
            setValidationColorError(R.color.pmvalidation_error).
            build();
    }

    public int getTextInputTheme() {
        return textInputTheme;
    }


    public int getButtonTheme() {
        return buttonTheme;
    }

    public int getButtonLabelStyle() {
        return buttonLabelStyle;
    }

    public int getCheckBoxTheme() {
        return checkBoxTheme;
    }

    public int getCheckBoxLabelCheckedStyle() {
        return checkBoxLabelCheckedStyle;
    }

    public int getCheckBoxLabelUncheckedStyle() {
        return checkBoxLabelUncheckedStyle;
    }

    public int getSelectLabelStyle() {
        return selectLabelStyle;
    }
    
    public int getInputTypeIcon(String inputType) {

        if (iconMapping.containsKey(inputType)) {
            return iconMapping.get(inputType);
        }
        return R.drawable.ic_default;
    }

    public int getValidationColorOk() {
        return validationColorOk;
    }

    public int getValidationColorUnknown() {
        return validationColorUnknown;
    }

    public int getValidationColorError() {
        return validationColorError;
    }

    public final static class Builder {
        int textInputTheme;
        int buttonTheme;
        int buttonLabelStyle;
        int checkBoxTheme;
        int checkBoxLabelCheckedStyle;
        int checkBoxLabelUncheckedStyle;
        int selectLabelStyle;
        Map<String, Integer> iconMapping;
        int validationColorUnknown;
        int validationColorOk;
        int validationColorError;

        Builder() {
            iconMapping = new HashMap<>();
        }

        public Builder setTextInputTheme(int textInputTheme) {
            this.textInputTheme = textInputTheme;
            return this;
        }

        public Builder setButtonTheme(int buttonTheme) {
            this.buttonTheme = buttonTheme;
            return this;
        }

        public Builder setButtonLabelStyle(int buttonLabelStyle) {
            this.buttonLabelStyle = buttonLabelStyle;
            return this;
        }

        public Builder setCheckBoxTheme(int checkBoxTheme) {
            this.checkBoxTheme = checkBoxTheme;
            return this;
        }

        public Builder setCheckBoxLabelCheckedStyle(int checkBoxLabelCheckedStyle) {
            this.checkBoxLabelCheckedStyle = checkBoxLabelCheckedStyle;
            return this;
        }

        public Builder setCheckBoxLabelUncheckedStyle(int checkBoxLabelUncheckedStyle) {
            this.checkBoxLabelUncheckedStyle = checkBoxLabelUncheckedStyle;
            return this;
        }

        public Builder setSelectLabelStyle(int selectLabelStyle) {
            this.selectLabelStyle = selectLabelStyle;
            return this;
        }
        
        public Builder putInputTypeIcon(String inputType, int iconRes) {
            iconMapping.put(inputType, iconRes);
            return this;
        }

        public Builder setDefaultIconMapping() {
            iconMapping.put(PaymentInputType.HOLDER_NAME, R.drawable.ic_name);
            iconMapping.put(PaymentInputType.EXPIRY_DATE, R.drawable.ic_date);
            iconMapping.put(PaymentInputType.EXPIRY_MONTH, R.drawable.ic_date);
            iconMapping.put(PaymentInputType.EXPIRY_YEAR, R.drawable.ic_date);
            iconMapping.put(PaymentInputType.BANK_CODE, R.drawable.ic_card);
            iconMapping.put(PaymentInputType.ACCOUNT_NUMBER, R.drawable.ic_card);
            iconMapping.put(PaymentInputType.IBAN, R.drawable.ic_card);
            iconMapping.put(PaymentInputType.BIC, R.drawable.ic_card);
            iconMapping.put(PaymentInputType.VERIFICATION_CODE, R.drawable.ic_lock);
            return this;
        }

        public Builder setValidationColorOk(int validationColorOk) {
            this.validationColorOk = validationColorOk;
            return this;
        }

        public Builder setValidationColorUnknown(int validationColorUnknown) {
            this.validationColorUnknown = validationColorUnknown;
            return this;
        }

        public Builder setValidationColorError(int validationColorError) {
            this.validationColorError = validationColorError;
            return this;
        }

        public WidgetParameters build() {
            return new WidgetParameters(this);
        }
    }
}
