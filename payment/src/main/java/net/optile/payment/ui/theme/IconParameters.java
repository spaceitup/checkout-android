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

import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

/**
 * Class for holding the Icon parameters for the PaymentTheme
 */
public final class IconParameters {

    private final Map<String, Integer> mapping;
    private int unknownColorResId;
    private int okColorResId;
    private int errorColorResId;

    IconParameters() {
        this.mapping = new HashMap<>();
    }

    IconParameters(Map<String, Integer> mapping) {
        this.mapping = mapping;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public int getOkColorResId() {
        return okColorResId;
    }

    public int getUnknownColorResId() {
        return unknownColorResId;
    }

    public int getErrorColorResId() {
        return errorColorResId;
    }

    public int getInputTypeIcon(String inputType) {

        if (mapping.containsKey(inputType)) {
            return mapping.get(inputType);
        }
        return R.drawable.ic_default;
    }

    public final static IconParameters createDefault() {
        return createBuilder().
            setDefaultIconMapping().
            setOkColorResId(R.color.pmvalidation_ok).
            setUnknownColorResId(R.color.pmvalidation_unknown).
            setErrorColorResId(R.color.pmvalidation_error).
            build();
    }
    
    public final static class Builder {
        Map<String, Integer> mapping;
        int okColorResId;
        int unknownColorResId;
        int errorColorResId;

        Builder() {
            mapping = new HashMap<String, Integer>();
        }

        public Builder setDefaultIconMapping() {
            mapping.put(PaymentInputType.HOLDER_NAME, R.drawable.ic_name);
            mapping.put(PaymentInputType.EXPIRY_DATE, R.drawable.ic_date);
            mapping.put(PaymentInputType.EXPIRY_MONTH, R.drawable.ic_date);
            mapping.put(PaymentInputType.EXPIRY_YEAR, R.drawable.ic_date);
            mapping.put(PaymentInputType.BANK_CODE, R.drawable.ic_card);
            mapping.put(PaymentInputType.ACCOUNT_NUMBER, R.drawable.ic_card);
            mapping.put(PaymentInputType.IBAN, R.drawable.ic_card);
            mapping.put(PaymentInputType.BIC, R.drawable.ic_card);
            mapping.put(PaymentInputType.VERIFICATION_CODE, R.drawable.ic_lock);
            return this;
        }
        
        public Builder putInputTypeIcon(String inputType, int iconRes) {
            mapping.put(inputType, iconRes);
            return this;
        }

        public Builder setOkColorResId(int okColorResId) {
            this.okColorResId = okColorResId;
            return this;
        }

        public Builder setUnknownColorResId(int unknownColorResId) {
            this.unknownColorResId = unknownColorResId;
            return this;
        }

        public Builder setErrorColorResId(int errorColorResId) {
            this.errorColorResId = errorColorResId;
            return this;
        }

        public IconParameters build() {
            IconParameters params = new IconParameters(this.mapping);
            params.okColorResId = this.okColorResId;
            params.unknownColorResId = this.unknownColorResId;
            params.errorColorResId = this.errorColorResId;
            return params;
        }
    }
}
