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
 * Class for holding the PaymentPage theme parameters.
 * These parameters may be used to theme the PaymentPage UI elements and text appearances.
 */
public final class PageParameters {
    private final int pageTheme;
    private final int emptyListLabelStyle;
    private final int sectionHeaderLabelStyle;
    private final int presetCardTitleStyle;
    private final int presetCardSubtitleStyle;
    private final int accountCardTitleStyle;
    private final int accountCardSubtitleStyle;
    private final int networkCardTitleStyle;
    private final int paymentLogoBackground;

    private PageParameters(Builder builder) {
        this.pageTheme = builder.pageTheme;
        this.emptyListLabelStyle = builder.emptyListLabelStyle;
        this.sectionHeaderLabelStyle = builder.sectionHeaderLabelStyle;
        this.presetCardTitleStyle = builder.presetCardTitleStyle;
        this.presetCardSubtitleStyle = builder.presetCardSubtitleStyle;
        this.accountCardTitleStyle = builder.accountCardTitleStyle;
        this.accountCardSubtitleStyle = builder.accountCardSubtitleStyle;
        this.networkCardTitleStyle = builder.networkCardTitleStyle;
        this.paymentLogoBackground = builder.paymentLogoBackground;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PageParameters createDefault() {
        return createBuilder().
            setPageTheme(R.style.PaymentTheme_PaymentPage).
            setEmptyListLabelStyle(R.style.PaymentText_Medium_Gray).
            setSectionHeaderLabelStyle(R.style.PaymentText_Medium_Bold).
            setPresetCardTitleStyle(R.style.PaymentText_Medium_Bold).
            setPresetCardSubtitleStyle(R.style.PaymentText_Tiny).
            setAccountCardTitleStyle(R.style.PaymentText_Medium_Bold).
            setAccountCardSubtitleStyle(R.style.PaymentText_Tiny).
            setNetworkCardTitleStyle(R.style.PaymentText_Medium).
            build();
    }

    public int getPageTheme() {
        return pageTheme;
    }

    public int getEmptyListLabelStyle() {
        return emptyListLabelStyle;
    }

    public int getSectionHeaderLabelStyle() {
        return sectionHeaderLabelStyle;
    }

    public int getPresetCardTitleStyle() {
        return presetCardTitleStyle;
    }

    public int getPresetCardSubtitleStyle() {
        return presetCardSubtitleStyle;
    }

    public int getAccountCardTitleStyle() {
        return accountCardTitleStyle;
    }

    public int getAccountCardSubtitleStyle() {
        return accountCardSubtitleStyle;
    }

    public int getNetworkCardTitleStyle() {
        return networkCardTitleStyle;
    }

    public int getPaymentLogoBackground() {
        return paymentLogoBackground;
    }

    public final static class Builder {
        int pageTheme;
        int emptyListLabelStyle;
        int sectionHeaderLabelStyle;
        int accountCardTitleStyle;
        int accountCardSubtitleStyle;
        int presetCardTitleStyle;
        int presetCardSubtitleStyle;
        int networkCardTitleStyle;
        int paymentLogoBackground;

        Builder() {
        }

        public Builder setPageTheme(int pageTheme) {
            this.pageTheme = pageTheme;
            return this;
        }

        public Builder setEmptyListLabelStyle(int emptyListLabelStyle) {
            this.emptyListLabelStyle = emptyListLabelStyle;
            return this;
        }

        public Builder setSectionHeaderLabelStyle(int sectionHeaderLabelStyle) {
            this.sectionHeaderLabelStyle = sectionHeaderLabelStyle;
            return this;
        }

        public Builder setPresetCardTitleStyle(int presetCardTitleStyle) {
            this.presetCardTitleStyle = presetCardTitleStyle;
            return this;
        }

        public Builder setPresetCardSubtitleStyle(int presetCardSubtitleStyle) {
            this.presetCardSubtitleStyle = presetCardSubtitleStyle;
            return this;
        }

        public Builder setAccountCardTitleStyle(int accountCardTitleStyle) {
            this.accountCardTitleStyle = accountCardTitleStyle;
            return this;
        }

        public Builder setAccountCardSubtitleStyle(int accountCardSubtitleStyle) {
            this.accountCardSubtitleStyle = accountCardSubtitleStyle;
            return this;
        }

        public Builder setNetworkCardTitleStyle(int networkCardTitleStyle) {
            this.networkCardTitleStyle = networkCardTitleStyle;
            return this;
        }

        public Builder setPaymentLogoBackground(int paymentLogoBackground) {
            this.paymentLogoBackground = paymentLogoBackground;
            return this;
        }

        public PageParameters build() {
            return new PageParameters(this);
        }
    }
}
