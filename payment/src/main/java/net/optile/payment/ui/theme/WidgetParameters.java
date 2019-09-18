/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.AnyRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StyleRes;
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
    private final int buttonBackground;
    private final int checkBoxTheme;
    private final int checkBoxLabelCheckedStyle;
    private final int checkBoxLabelUncheckedStyle;
    private final int selectLabelStyle;
    private final int validationColorUnknown;
    private final int validationColorOk;
    private final int validationColorError;
    private final int hintDrawable;
    private final int infoLabelStyle;

    private WidgetParameters(Builder builder) {
        this.iconMapping = new HashMap<>(builder.iconMapping);
        this.textInputTheme = builder.textInputTheme;
        this.buttonTheme = builder.buttonTheme;
        this.buttonLabelStyle = builder.buttonLabelStyle;
        this.buttonBackground = builder.buttonBackground;
        this.checkBoxTheme = builder.checkBoxTheme;
        this.checkBoxLabelCheckedStyle = builder.checkBoxLabelCheckedStyle;
        this.checkBoxLabelUncheckedStyle = builder.checkBoxLabelUncheckedStyle;
        this.selectLabelStyle = builder.selectLabelStyle;
        this.validationColorOk = builder.validationColorOk;
        this.validationColorUnknown = builder.validationColorUnknown;
        this.validationColorError = builder.validationColorError;
        this.hintDrawable = builder.hintDrawable;
        this.infoLabelStyle = builder.infoLabelStyle;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static WidgetParameters createDefault() {
        return createBuilder().
            setTextInputTheme(R.style.PaymentThemeTextInput).
            setButtonTheme(R.style.PaymentThemeButton).
            setButtonLabelStyle(R.style.PaymentText_Medium_Bold_Light).
            setButtonBackground(R.drawable.button_background).
            setCheckBoxTheme(R.style.PaymentThemeCheckBox).
            setCheckBoxLabelCheckedStyle(R.style.PaymentText_Medium).
            setCheckBoxLabelUncheckedStyle(R.style.PaymentText_Medium_Hint).
            setSelectLabelStyle(R.style.PaymentText_Tiny).
            setDefaultIconMapping().
            setValidationColorOk(R.color.pmvalidation_ok).
            setValidationColorUnknown(R.color.pmvalidation_unknown).
            setValidationColorError(R.color.pmvalidation_error).
            setHintDrawable(R.drawable.ic_hint).
            setInfoLabelStyle(R.style.PaymentText_Small).
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

    public int getButtonBackground() {
        return buttonBackground;
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
        Integer val = iconMapping.get(inputType);
        return val != null ? val : R.drawable.ic_default;
    }

    public int getHintDrawable() {
        return hintDrawable;
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

    public int getInfoLabelStyle() {
        return infoLabelStyle;
    }

    public final static class Builder {
        final Map<String, Integer> iconMapping;
        int textInputTheme;
        int buttonTheme;
        int buttonLabelStyle;
        int buttonBackground;
        int checkBoxTheme;
        int checkBoxLabelCheckedStyle;
        int checkBoxLabelUncheckedStyle;
        int selectLabelStyle;
        int validationColorUnknown;
        int validationColorOk;
        int validationColorError;
        int hintDrawable;
        int infoLabelStyle;

        Builder() {
            iconMapping = new HashMap<>();
        }

        public Builder setTextInputTheme(@StyleRes int textInputTheme) {
            this.textInputTheme = textInputTheme;
            return this;
        }

        public Builder setButtonTheme(@StyleRes int buttonTheme) {
            this.buttonTheme = buttonTheme;
            return this;
        }

        public Builder setButtonLabelStyle(@StyleRes int buttonLabelStyle) {
            this.buttonLabelStyle = buttonLabelStyle;
            return this;
        }

        public Builder setButtonBackground(@AnyRes int buttonBackground) {
            this.buttonBackground = buttonBackground;
            return this;
        }

        public Builder setCheckBoxTheme(@StyleRes int checkBoxTheme) {
            this.checkBoxTheme = checkBoxTheme;
            return this;
        }

        public Builder setCheckBoxLabelCheckedStyle(@StyleRes int checkBoxLabelCheckedStyle) {
            this.checkBoxLabelCheckedStyle = checkBoxLabelCheckedStyle;
            return this;
        }

        public Builder setCheckBoxLabelUncheckedStyle(@StyleRes int checkBoxLabelUncheckedStyle) {
            this.checkBoxLabelUncheckedStyle = checkBoxLabelUncheckedStyle;
            return this;
        }

        public Builder setSelectLabelStyle(@StyleRes int selectLabelStyle) {
            this.selectLabelStyle = selectLabelStyle;
            return this;
        }

        public Builder putInputTypeIcon(String inputType, @DrawableRes int iconRes) {
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

        public Builder setDefaultHintDrawable() {
            this.hintDrawable = R.drawable.ic_hint;
            return this;
        }

        public Builder setValidationColorOk(@ColorRes int validationColorOk) {
            this.validationColorOk = validationColorOk;
            return this;
        }

        public Builder setValidationColorUnknown(@ColorRes int validationColorUnknown) {
            this.validationColorUnknown = validationColorUnknown;
            return this;
        }

        public Builder setValidationColorError(@ColorRes int validationColorError) {
            this.validationColorError = validationColorError;
            return this;
        }

        public Builder setHintDrawable(int hintDrawable) {
            this.hintDrawable = hintDrawable;
            return this;
        }

        public Builder setInfoLabelStyle(int infoLabelStyle) {
            this.infoLabelStyle = infoLabelStyle;
            return this;
        }

        public WidgetParameters build() {
            return new WidgetParameters(this);
        }
    }
}
