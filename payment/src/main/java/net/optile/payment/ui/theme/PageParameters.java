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
 * Class for holding the PaymentPage theme parameters
 */
public final class PageParameters {

    private int themeResId;

    private int emptyTextAppearance;

    PageParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public int getThemeResId() {
        return themeResId;
    }

    public int getEmptyTextAppearance() {
        return emptyTextAppearance;
    }

    public static final PageParameters createDefault() {
        return createBuilder().
            setThemeResId(R.style.PaymentTheme_PaymentPage).
            setEmptyTextAppearance(R.style.PaymentText_Medium_Gray).
            build();
    }

    public final static class Builder {
        int themeResId;
        int emptyTextAppearance;

        Builder() {
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setEmptyTextAppearance(int emptyTextAppearance) {
            this.emptyTextAppearance = emptyTextAppearance;
            return this;
        }

        public PageParameters build() {
            PageParameters params = new PageParameters();
            params.themeResId = this.themeResId;
            params.emptyTextAppearance = this.emptyTextAppearance;
            return params;
        }
    }
}
