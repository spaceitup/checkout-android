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
 * Class for holding the InputTextParameters for the PaymentTheme
 */
public final class TextInputParameters {
    private int themeResId;

    TextInputParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public int getThemeResId() {
        return themeResId;
    }

    public final static TextInputParameters createDefault() {
        return createBuilder().
            setThemeResId(R.style.PaymentThemeTextInput).
            build();
    }

    public final static class Builder {
        int themeResId;

        Builder() {
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public TextInputParameters build() {
            TextInputParameters params = new TextInputParameters();
            params.themeResId = this.themeResId;
            return params;
        }
    }
}
