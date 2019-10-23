/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

/**
 * Class to hold the theme settings of the payment screens in the Android SDK
 */
public final class PaymentTheme {
    private final Map<String, Integer> iconMapping;
    private final int messageDialogTheme;
    private final int dateDialogTheme;
    private final int paymentListTheme;
    private final int chargePaymentTheme;
    private final int validationColorUnknown;
    private final int validationColorOk;
    private final int validationColorError;

    private PaymentTheme(Builder builder) {
        this.iconMapping = new HashMap<>(builder.iconMapping);

        this.messageDialogTheme = builder.messageDialogTheme;
        this.dateDialogTheme = builder.dateDialogTheme;
        this.paymentListTheme = builder.paymentListTheme;
        this.chargePaymentTheme = builder.chargePaymentTheme;

        this.validationColorOk = builder.validationColorOk;
        this.validationColorUnknown = builder.validationColorUnknown;
        this.validationColorError = builder.validationColorError;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PaymentTheme createDefault() {
        return createBuilder().
            setDefaultIconMapping().
            setMessageDialogTheme(R.style.PaymentDialogTheme_Message).
            setDateDialogTheme(R.style.PaymentDialogTheme_Date).
            setPaymentListTheme(R.style.PaymentTheme_PaymentList).
            setChargePaymentTheme(R.style.PaymentTheme_ChargePayment).
            setValidationColorOk(R.color.pmvalidation_ok).
            setValidationColorUnknown(R.color.pmvalidation_unknown).
            setValidationColorError(R.color.pmvalidation_error).
            build();
    }

    public int getInputTypeIcon(String inputType) {
        Integer val = iconMapping.get(inputType);
        return val != null ? val : R.drawable.ic_default;
    }

    public int getMessageDialogTheme() {
        return messageDialogTheme;
    }

    public int getDateDialogTheme() {
        return dateDialogTheme;
    }

    public int getPaymentListTheme() {
        return paymentListTheme;
    }

    public int getChargePaymentTheme() {
        return chargePaymentTheme;
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

    public static final class Builder {
        Map<String, Integer> iconMapping;
        int messageDialogTheme;
        int dateDialogTheme;
        int paymentListTheme;
        int chargePaymentTheme;
        int validationColorUnknown;
        int validationColorOk;
        int validationColorError;

        Builder() {
            iconMapping = new HashMap<>();
        }

        public Builder setMessageDialogTheme(int messageDialogTheme) {
            this.messageDialogTheme = messageDialogTheme;
            return this;
        }

        public Builder setDateDialogTheme(int dateDialogTheme) {
            this.dateDialogTheme = dateDialogTheme;
            return this;
        }

        public Builder setPaymentListTheme(int paymentListTheme) {
            this.paymentListTheme = paymentListTheme;
            return this;
        }

        public Builder setChargePaymentTheme(int chargePaymentTheme) {
            this.chargePaymentTheme = chargePaymentTheme;
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

        public PaymentTheme build() {
            return new PaymentTheme(this);
        }
    }
}
