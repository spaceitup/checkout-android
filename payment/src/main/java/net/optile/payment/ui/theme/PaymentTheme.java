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

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme {

    private PaymentPageParameters paymentPageParameters;
    private IconParameters iconParameters;
    private ButtonWidgetParameters buttonWidgetParameters;
    private CheckBoxWidgetParameters checkBoxWidgetParameters;
    private TextInputWidgetParameters textInputWidgetParameters;
    
    private PaymentTheme() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public PaymentPageParameters getPaymentPageParameters() {
        return paymentPageParameters;
    }

    public IconParameters getIconParameters() {
        return iconParameters;
    }

    public ButtonWidgetParameters getButtonWidgetParameters() {
        return buttonWidgetParameters;
    }

    public CheckBoxWidgetParameters getCheckBoxWidgetParameters() {
        return checkBoxWidgetParameters;
    }

    public TextInputWidgetParameters getTextInputWidgetParameters() {
        return textInputWidgetParameters;
    }

    public static final class Builder {
        PaymentPageParameters paymentPageParameters;
        IconParameters iconParameters;
        ButtonWidgetParameters buttonWidgetParameters;
        CheckBoxWidgetParameters checkBoxWidgetParameters;
        TextInputWidgetParameters textInputWidgetParameters;
        
        Builder() {
        }

        public Builder setPaymentPageParameters(PaymentPageParameters paymentPageParameters) {
            this.paymentPageParameters = paymentPageParameters;
            return this;
        }

        public Builder setIconParameters(IconParameters iconParameters) {
            this.iconParameters = iconParameters;
            return this;
        }

        public Builder setButtonWidgetParameters(ButtonWidgetParameters buttonWidgetParameters) {
            this.buttonWidgetParameters = buttonWidgetParameters;
            return this;
        }

        public Builder setCheckBoxWidgetParameters(CheckBoxWidgetParameters checkBoxWidgetParameters) {
            this.checkBoxWidgetParameters = checkBoxWidgetParameters;
            return this;
        }

        public Builder setTextInputWidgetParameters(TextInputWidgetParameters textInputWidgetParameters) {
            this.textInputWidgetParameters = textInputWidgetParameters;
            return this;
        }
        
        public PaymentTheme build() {
            PaymentTheme theme = new PaymentTheme();

            if (paymentPageParameters == null) {
                paymentPageParameters = PaymentPageParameters.createBuilder().build();
            }
            theme.paymentPageParameters = paymentPageParameters;

            if (iconParameters == null) {
                iconParameters = IconParameters.createBuilder().build();
            }
            theme.iconParameters = iconParameters;

            if (buttonWidgetParameters == null) {
                buttonWidgetParameters = ButtonWidgetParameters.createBuilder().build();
            }
            theme.buttonWidgetParameters = buttonWidgetParameters;

            if (checkBoxWidgetParameters == null) {
                checkBoxWidgetParameters = CheckBoxWidgetParameters.createBuilder().build();
            }
            theme.checkBoxWidgetParameters = checkBoxWidgetParameters;

            if (textInputWidgetParameters == null) {
                textInputWidgetParameters = TextInputWidgetParameters.createBuilder().build();
            }
            theme.textInputWidgetParameters = textInputWidgetParameters;
            return theme;
        }
    }
}

