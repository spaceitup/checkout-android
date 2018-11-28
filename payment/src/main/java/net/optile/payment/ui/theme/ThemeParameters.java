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

import net.optile.payment.R;

/**
 * Class for holding the Theme parameters for the PaymentTheme
 */
public final class ThemeParameters {

    private int paymentPageTheme;
    private int buttonTheme;
    private int checkBoxTheme;
    private int toolbarTheme;
    private int textInputTheme;
    private int cardViewTheme;
    
    ThemeParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }
    
    public int getPaymentPageTheme() {
        return paymentPageTheme;
    }

    public int getButtonTheme() {
        return buttonTheme;
    }

    public int getToolbarTheme() {
        return toolbarTheme;
    }

    public int getCheckBoxTheme() {
        return checkBoxTheme;
    }

    public int getTextInputTheme() {
        return textInputTheme;
    }

    public int getCardViewTheme() {
        return cardViewTheme;
    }
    
    public final static class Builder {

        private int paymentPageTheme;
        private int buttonTheme;
        private int checkBoxTheme;
        private int toolbarTheme;
        private int textInputTheme;
        private int cardViewTheme;
        
        private Builder() {
            this.paymentPageTheme = R.style.PaymentTheme_PaymentPage;
            this.buttonTheme = R.style.PaymentThemeButton;
            this.toolbarTheme = R.style.PaymentThemeToolbar;
            this.checkBoxTheme = R.style.PaymentThemeCheckBox;
            this.textInputTheme = R.style.PaymentThemeTextInput;
            this.cardViewTheme = R.style.PaymentThemeCardView;
        }

        public Builder setPaymentPageTheme(int paymentPageTheme) {
            this.paymentPageTheme = paymentPageTheme;
            return this;
        }

        public Builder setButtonTheme(int buttonTheme) {
            this.buttonTheme = buttonTheme;
            return this;
        }

        public Builder setToolbarTheme(int toolbarTheme) {
            this.toolbarTheme = toolbarTheme;
            return this;
        }

        public Builder setCheckBoxTheme(int checkBoxTheme) {
            this.checkBoxTheme = checkBoxTheme;
            return this;
        }

        public Builder setTextInputTheme(int textInputTheme) {
            this.textInputTheme = textInputTheme;
            return this;
        }

        public Builder setCardViewTheme(int cardViewTheme) {
            this.cardViewTheme = cardViewTheme;
            return this;
        }
        
        public ThemeParameters build() {
            ThemeParameters params = new ThemeParameters();
            params.paymentPageTheme = this.paymentPageTheme;
            params.buttonTheme = this.buttonTheme;
            params.toolbarTheme = this.toolbarTheme;
            params.checkBoxTheme = this.checkBoxTheme;
            params.textInputTheme = this.textInputTheme;
            params.cardViewTheme = this.cardViewTheme;
            return params;
        }
    }
}

