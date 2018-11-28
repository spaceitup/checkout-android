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

import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme {

    private ThemeParameters themeParameters;
    private TextAppearanceParameters textAppearanceParameters;
    private IconParameters iconParameters;
    private ValidationParameters validationParameters;

    private PaymentTheme() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }
    
    public ThemeParameters getThemeParameters() {
        return themeParameters;
    }

    public TextAppearanceParameters getTextAppearanceParameters() {
        return textAppearanceParameters;
    }

    public IconParameters getIconParameters() {
        return iconParameters;
    }

    public ValidationParameters getValidationParameters() {
        return validationParameters;
    }

    public static final class Builder {
        ThemeParameters themeParameters;
        TextAppearanceParameters textAppearanceParameters;
        IconParameters iconParameters;
        ValidationParameters validationParameters;
        
        Builder() {
        }
        
        public Builder setThemeParameters(ThemeParameters themeParameters) {
            this.themeParameters = themeParameters;
            return this;
        }

        public Builder setTextAppearanceParameters(TextAppearanceParameters textAppearanceParameters) {
            this.textAppearanceParameters = textAppearanceParameters;
            return this;
        }

        public Builder setIconParameters(IconParameters iconParameters) {
            this.iconParameters = iconParameters;
            return this;
        }

        public Builder setValidationParameters(ValidationParameters validationParameters) {
            this.validationParameters = validationParameters;
            return this;
        }

        public PaymentTheme build() {
            PaymentTheme theme = new PaymentTheme();

            if (themeParameters == null) {
                themeParameters = new ThemeParameters();
            }
            theme.themeParameters = themeParameters;

            if (textAppearanceParameters == null) {
                textAppearanceParameters = new TextAppearanceParameters();
            }
            theme.textAppearanceParameters = textAppearanceParameters;

            if (iconParameters == null) {
                iconParameters = new IconParameters();
            }
            theme.iconParameters = iconParameters;
            
            if (validationParameters == null) {
                validationParameters = new ValidationParameters();
            }
            theme.validationParameters = validationParameters;
            return theme;
        }
    }
}

