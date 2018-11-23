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

package net.optile.payment.ui;

import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme {

    private int paymentPageTheme;
    private int buttonTheme;
    private int checkBoxTheme;
    private int toolbarTheme;
    private int textInputTheme;
    
    private PaymentTheme() {
        this.paymentPageTheme = R.style.PaymentTheme_PaymentPage;
        this.toolbarTheme = R.style.PaymentThemeToolbar;
        this.buttonTheme = R.style.PaymentThemeButton;
        this.checkBoxTheme = R.style.PaymentThemeCheckBox;
        this.textInputTheme = R.style.PaymentThemeTextInput;
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
    
    public int getWidgetIconRes(String name) {

        if (TextUtils.isEmpty(name)) {
            return R.drawable.ic_default;
        }
        switch (name) {
            case PaymentInputType.HOLDER_NAME:
                return R.drawable.ic_name;
            case PaymentInputType.EXPIRY_DATE:
            case PaymentInputType.EXPIRY_MONTH:
            case PaymentInputType.EXPIRY_YEAR:
                return R.drawable.ic_date;
            case PaymentInputType.BANK_CODE:
            case PaymentInputType.ACCOUNT_NUMBER:
            case PaymentInputType.IBAN:
            case PaymentInputType.BIC:
                return R.drawable.ic_card;
            case PaymentInputType.VERIFICATION_CODE:
                return R.drawable.ic_lock;
            default:
                return R.drawable.ic_default;
        }
    }

    /**
     * Create a new PaymentThemeBuilder, this builder can be used to build a new PaymentTheme
     *
     * @return the newly created PaymentThemeBuilder
     */
    public static PaymentThemeBuilder createPaymentThemeBuilder() {
        return new PaymentThemeBuilder();
    }

    /**
     * Class PaymentThemeBuilder for building PaymentTheme
     */
    public static class PaymentThemeBuilder {

        private int paymentPageTheme;
        private int buttonTheme;
        private int checkBoxTheme;
        private int toolbarTheme;
        private int textInputTheme;
        
        private PaymentThemeBuilder() {
            this.paymentPageTheme = R.style.PaymentTheme_PaymentPage;
            this.buttonTheme = R.style.PaymentThemeButton;
            this.toolbarTheme = R.style.PaymentThemeToolbar;
            this.checkBoxTheme = R.style.PaymentThemeCheckBox;
            this.textInputTheme = R.style.PaymentThemeTextInput;
        }

        public PaymentThemeBuilder setPaymentPageTheme(int paymentPageTheme) {
            this.paymentPageTheme = paymentPageTheme;
            return this;
        }

        public PaymentThemeBuilder setButtonTheme(int buttonTheme) {
            this.buttonTheme = buttonTheme;
            return this;
        }

        public PaymentThemeBuilder setToolbarTheme(int toolbarTheme) {
            this.toolbarTheme = toolbarTheme;
            return this;
        }

        public PaymentThemeBuilder setCheckBoxTheme(int checkBoxTheme) {
            this.checkBoxTheme = checkBoxTheme;
            return this;
        }

        public PaymentThemeBuilder setTextInputTheme(int textInputTheme) {
            this.textInputTheme = textInputTheme;
            return this;
        }
        
        public PaymentTheme build() {
            PaymentTheme theme = new PaymentTheme();
            theme.paymentPageTheme = this.paymentPageTheme;
            theme.buttonTheme = this.buttonTheme;
            theme.toolbarTheme = this.toolbarTheme;
            theme.checkBoxTheme = this.checkBoxTheme;
            theme.textInputTheme = this.textInputTheme;
            return theme;
        }
    }
}
