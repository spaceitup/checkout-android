/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

import android.support.annotation.AnyRes;
import android.support.annotation.ColorRes;
import android.support.annotation.StyleRes;
import net.optile.payment.R;

/**
 * Class for holding the Progress (load and send) theme parameters.
 * These parameters may be used to theme the progress (loading) UI elements and text appearances.
 */
public final class ProgressParameters {

    private final int loadBackground;
    private final int loadProgressBarColor;
    private final int sendBackground;
    private final int sendProgressBarColorFront;
    private final int sendProgressBarColorBack;
    private final int headerStyle;
    private final int infoStyle;

    private ProgressParameters(Builder builder) {
        this.loadBackground = builder.loadBackground;
        this.loadProgressBarColor = builder.loadProgressBarColor;
        this.sendBackground = builder.sendBackground;
        this.sendProgressBarColorFront = builder.sendProgressBarColorFront;
        this.sendProgressBarColorBack = builder.sendProgressBarColorBack;
        this.headerStyle = builder.headerStyle;
        this.infoStyle = builder.infoStyle;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static ProgressParameters createDefault() {
        return createBuilder().
            setLoadBackground(R.color.pmprogress_background).
            setLoadProgressBarColor(R.color.pmcolor_primary).
            setSendBackground(R.color.pmcolor_background).
            setSendProgressBarColorFront(R.color.pmcolor_primary).
            setSendProgressBarColorBack(R.color.pmvalidation_unknown).
            setHeaderStyle(R.style.PaymentText_XLarge_Bold).
            setInfoStyle(R.style.PaymentText_Medium).
            build();
    }

    public int getLoadBackground() {
        return loadBackground;
    }

    public int getLoadProgressBarColor() {
        return loadProgressBarColor;
    }

    public int getSendBackground() {
        return sendBackground;
    }

    public int getSendProgressBarColorFront() {
        return sendProgressBarColorFront;
    }

    public int getSendProgressBarColorBack() {
        return sendProgressBarColorBack;
    }

    public int getHeaderStyle() {
        return headerStyle;
    }

    public int getInfoStyle() {
        return infoStyle;
    }

    public final static class Builder {
        int loadBackground;
        int loadProgressBarColor;
        int sendBackground;
        int sendProgressBarColorFront;
        int sendProgressBarColorBack;
        int headerStyle;
        int infoStyle;

        Builder() {
        }

        public Builder setLoadBackground(@AnyRes int loadBackground) {
            this.loadBackground = loadBackground;
            return this;
        }

        public Builder setLoadProgressBarColor(@ColorRes int loadProgressBarColor) {
            this.loadProgressBarColor = loadProgressBarColor;
            return this;
        }

        public Builder setSendBackground(@AnyRes int sendBackground) {
            this.sendBackground = sendBackground;
            return this;
        }

        public Builder setSendProgressBarColorFront(@ColorRes int sendProgressBarColorFront) {
            this.sendProgressBarColorFront = sendProgressBarColorFront;
            return this;
        }

        public Builder setSendProgressBarColorBack(@ColorRes int sendProgressBarColorBack) {
            this.sendProgressBarColorBack = sendProgressBarColorBack;
            return this;
        }

        public Builder setHeaderStyle(@StyleRes int headerStyle) {
            this.headerStyle = headerStyle;
            return this;
        }

        public Builder setInfoStyle(@StyleRes int infoStyle) {
            this.infoStyle = infoStyle;
            return this;
        }

        public ProgressParameters build() {
            return new ProgressParameters(this);
        }
    }
}
