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
            setLoadBackground(R.color.pmcolor_list).
            setLoadProgressBarColor(R.color.pmcolor_primary).
            setSendBackground(R.color.pmcolor_list).
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

        public Builder setLoadBackground(int loadBackground) {
            this.loadBackground = loadBackground;
            return this;
        }

        public Builder setLoadProgressBarColor(int loadProgressBarColor) {
            this.loadProgressBarColor = loadProgressBarColor;
            return this;
        }

        public Builder setSendBackground(int sendBackground) {
            this.sendBackground = sendBackground;
            return this;
        }

        public Builder setSendProgressBarColorFront(int sendProgressBarColorFront) {
            this.sendProgressBarColorFront = sendProgressBarColorFront;
            return this;
        }

        public Builder setSendProgressBarColorBack(int sendProgressBarColorBack) {
            this.sendProgressBarColorBack = sendProgressBarColorBack;
            return this;
        }
        
        public Builder setHeaderStyle(int headerStyle) {
            this.headerStyle = headerStyle;
            return this;
        }

        public Builder setInfoStyle(int infoStyle) {
            this.infoStyle = infoStyle;
            return this;
        }

        public ProgressParameters build() {
            return new ProgressParameters(this);
        }
    }
}
