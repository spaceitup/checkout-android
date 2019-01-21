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
    private final int loadProgressBarTheme;    
    private final int sendBackground;    
    private final int sendProgressBarTheme;
    private final int headerStyle;
    private final int infoStyle;
    
    private ProgressParameters(Builder builder) {
        this.loadBackground = builder.loadBackground;
        this.loadProgressBarTheme = builder.loadProgressBarTheme;
        this.sendBackground = builder.sendBackground;
        this.sendProgressBarTheme = builder.sendProgressBarTheme;
        this.headerStyle = builder.headerStyle;
        this.infoStyle = builder.infoStyle;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static ProgressParameters createDefault() {
        return createBuilder().
            setLoadBackground(R.color.pmcolor_list).
            setLoadProgressBarTheme(R.style.PaymentThemeProgressBarLoad).
            setSendBackground(R.color.pmcolor_list).
            setSendProgressBarTheme(R.style.PaymentThemeProgressBarSend).
            setHeaderStyle(R.style.PaymentText_XLarge_Bold).
            setInfoStyle(R.style.PaymentText_Medium).            
            build();
    }

    public int getLoadBackground() {
        return loadBackground;
    }

    public int getLoadProgressBarTheme() {
        return loadProgressBarTheme;
    }

    public int getSendBackground() {
        return sendBackground;
    }
    
    public int getSendProgressBarTheme() {
        return sendProgressBarTheme;
    }

    public int getHeaderStyle() {
        return headerStyle;
    }

    public int getInfoStyle() {
        return infoStyle;
    }
    
    public final static class Builder {
        int loadBackground;
        int loadProgressBarTheme;
        int sendBackground;
        int sendProgressBarTheme;
        int headerStyle;
        int infoStyle;
        
        Builder() {
        }

        public Builder setLoadBackground(int loadBackground) {
            this.loadBackground = loadBackground;
            return this;
        }
        
        public Builder setLoadProgressBarTheme(int loadProgressBarTheme) {
            this.loadProgressBarTheme = loadProgressBarTheme;
            return this;
        }

        public Builder setSendBackground(int sendBackground) {
            this.sendBackground = sendBackground;
            return this;
        }

        public Builder setSendProgressBarTheme(int sendProgressBarTheme) {
            this.sendProgressBarTheme = sendProgressBarTheme;
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
