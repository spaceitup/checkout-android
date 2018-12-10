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
    private int pageTheme;
    private int emptyListLabelStyle;
    private int sectionHeaderLabelStyle;
    private int accountCardTitleStyle;
    private int accountCardSubtitleStyle;
    private int networkCardTitleStyle;
    private int paymentLogoBackground;

    private PageParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PageParameters createDefault() {
        return createBuilder().
            setPageTheme(R.style.PaymentTheme_PaymentPage).
            setEmptyListLabelStyle(R.style.PaymentText_Medium_Gray).
            setSectionHeaderLabelStyle(R.style.PaymentText_Medium_Bold).
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
            PageParameters params = new PageParameters();
            params.pageTheme = this.pageTheme;
            params.emptyListLabelStyle = this.emptyListLabelStyle;
            params.sectionHeaderLabelStyle = this.sectionHeaderLabelStyle;
            params.accountCardTitleStyle = this.accountCardTitleStyle;
            params.accountCardSubtitleStyle = this.accountCardSubtitleStyle;
            params.networkCardTitleStyle = this.networkCardTitleStyle;
            params.paymentLogoBackground = this.paymentLogoBackground;
            return params;
        }
    }
}
