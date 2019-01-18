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
 * Class for holding the Progress theme parameters.
 * These parameters may be used to theme the progress (loading) UI elements and text appearances.
 */
public final class ProgressParameters {
    private final int loadBackground;
    private final int loadProgressBarTheme;    
    private final int sendBackground;    
    private final int sendProgressBarTheme;
    private final int sendHeaderStyle;
    private final int sendInfoStyle;
    
    private ProgressParameters(Builder builder) {
        this.loadBackground = builder.loadBackground;
        this.loadProgressBarTheme = builder.loadProgressBarTheme;
        this.sendBackground = builder.sendBackground;
        this.sendProgressBarTheme = builder.sendProgressBarTheme;
        this.sendHeaderStyle = builder.sendHeaderStyle;
        this.sendInfoStyle = builder.sendInfoStyle;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static ProgressParameters createDefault() {
        return createBuilder().
            setLoadProgressBarTheme(R.style.PaymentThemeLoadProgressBar).
            setSendProgressBarTheme(R.style.PaymentThemeSendProgressBar).
            setSendHeaderStyle(R.style.PaymentText_XLarge_Bold).
            setSendInfoStyle(R.style.PaymentText_Medium).            
            build();
    }

    public int getLoadBackground() {
        return loadBackground;
    }

    public int getLoadProgressBarTheme() {
        return progressBarLoadTheme;
    }

    public int getSendBackground() {
        return sendBackground;
    }
    
    public int getSendProgressBarTheme() {
        return progressBarSendTheme;
    }

    public int getSendHeaderStyle() {
        return progressSendHeaderStyle;
    }

    public int getSendInfoStyle() {
        return progressSendInfoStyle;
    }
    
    public final static class Builder {
        int loadBackground;
        int loadProgressBarTheme;
        int sendBackground;
        int sendProgressBarTheme;
        int sendHeaderStyle;
        int sendInfoStyle;
        
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

        public Builder setSendHeaderStyle(int sendHeaderStyle) {
            this.sendHeaderStyle = sendHeaderStyle;
            return this;
        }

        public Builder setSendInfoStyle(int sendInfoStyle) {
            this.sendInfoStyle = sendInfoStyle;
            return this;
        }
        
        public PageParameters build() {
            return new PageParameters(this);
        }
    }
}
