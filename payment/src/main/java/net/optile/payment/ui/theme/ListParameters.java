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
 * Class for holding the ListParameters for the PaymentTheme
 */
public final class ListParameters {

    private int headerTextAppearance;
    private int networkTitleTextAppearance;
    private int accountTitleTextAppearance;
    private int accountSubtitleTextAppearance;
    private int logoBackgroundResId;

    ListParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public int getHeaderTextAppearance() {
        return headerTextAppearance;
    }

    public int getAccountTitleTextAppearance() {
        return accountTitleTextAppearance;
    }

    public int getAccountSubtitleTextAppearance() {
        return accountSubtitleTextAppearance;
    }

    public int getNetworkTitleTextAppearance() {
        return networkTitleTextAppearance;
    }

    public int getLogoBackgroundResId() {
        return logoBackgroundResId;
    }

    public final static ListParameters createDefault() {
        return createBuilder().
            setHeaderTextAppearance(R.style.PaymentText_Medium_Bold).
            setAccountTitleTextAppearance(R.style.PaymentText_Medium_Bold).
            setAccountSubtitleTextAppearance(R.style.PaymentText_Tiny).    
            setNetworkTitleTextAppearance(R.style.PaymentText_Medium).
            build();
    }

    public final static class Builder {
        int headerTextAppearance;
        int accountTitleTextAppearance;
        int accountSubtitleTextAppearance;
        int networkTitleTextAppearance;
        int logoBackgroundResId;

        Builder() {
        }

        public Builder setHeaderTextAppearance(int headerTextAppearance) {
            this.headerTextAppearance = headerTextAppearance;
            return this;
        }

        public Builder setNetworkTitleTextAppearance(int networkTitleTextAppearance) {
            this.networkTitleTextAppearance = networkTitleTextAppearance;
            return this;
        }

        public Builder setAccountTitleTextAppearance(int accountTitleTextAppearance) {
            this.accountTitleTextAppearance = accountTitleTextAppearance;
            return this;
        }

        public Builder setAccountSubtitleTextAppearance(int accountSubtitleTextAppearance) {
            this.accountSubtitleTextAppearance = accountSubtitleTextAppearance;
            return this;
        }

        public Builder setLogoBackgroundResId(int logoBackgroundResId) {
            this.logoBackgroundResId = logoBackgroundResId;
            return this;
        }

        public ListParameters build() {
            ListParameters params = new ListParameters();
            params.headerTextAppearance = this.headerTextAppearance;
            params.accountTitleTextAppearance = this.accountTitleTextAppearance;
            params.accountSubtitleTextAppearance = this.accountSubtitleTextAppearance;
            params.networkTitleTextAppearance = this.networkTitleTextAppearance;
            params.logoBackgroundResId = this.logoBackgroundResId;
            return params;
        }
    }
}
