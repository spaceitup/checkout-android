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
    private ButtonParameters buttonParameters;
    private CheckBoxParameters checkBoxParameters;
    private DateParameters dateParameters;
    
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

    public ButtonParameters getButtonParameters() {
        return buttonParameters;
    }

    public CheckBoxParameters getCheckBoxParameters() {
        return checkBoxParameters;
    }

    public DateParameters getDateParameters() {
        return dateParameters;
    }
    
    public static final class Builder {
        PaymentPageParameters paymentPageParameters;
        IconParameters iconParameters;
        ButtonParameters buttonParameters;
        CheckBoxParameters checkBoxParameters;
        DateParameters dateParameters;
        
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

        public Builder setButtonParameters(ButtonParameters buttonParameters) {
            this.buttonParameters = buttonParameters;
            return this;
        }

        public Builder setCheckBoxParameters(CheckBoxParameters checkBoxParameters) {
            this.checkBoxParameters = checkBoxParameters;
            return this;
        }

        public Builder setDateParameters(DateParameters dateParameters) {
            this.dateParameters = dateParameters;
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

            if (buttonParameters == null) {
                buttonParameters = ButtonParameters.createBuilder().build();
            }
            theme.buttonParameters = buttonParameters;

            if (checkBoxParameters == null) {
                checkBoxParameters = CheckBoxParameters.createBuilder().build();
            }
            theme.checkBoxParameters = checkBoxParameters;

            if (dateParameters == null) {
                dateParameters = DateParameters.createBuilder().build();
            }
            theme.dateParameters = dateParameters;

            return theme;
        }
    }
}

