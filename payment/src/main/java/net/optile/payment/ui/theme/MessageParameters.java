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
 * Class for holding the MessageParameters for the PaymentTheme
 */
public final class MessageParameters {
    private int titleTextAppearance;
    private int messageTextAppearance;
    private int messageNoTitleTextAppearance;
    private int buttonTextAppearance;

    MessageParameters() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public int getTitleAppearance() {
        return titleTextAppearance;
    }

    public int getMessageTextAppearance() {
        return messageTextAppearance;
    }

    public int getMessageNoTitleTextAppearance() {
        return messageNoTitleTextAppearance;
    }

    public int getButtonTextAppearance() {
        return buttonTextAppearance;
    }

    public final static MessageParameters createDefault() {
        return createBuilder().
            setTitleTextAppearance(R.style.PaymentText_Large_Bold).
            setMessageTextAppearance(R.style.PaymentText_Medium_Gray).
            setMessageNoTitleTextAppearance(R.style.PaymentText_Medium_Bold_Gray).
            setButtonTextAppearance(R.style.PaymentText_Small_Bold_Primary).  
            build();
    }

    public final static class Builder {
        int titleTextAppearance;
        int messageTextAppearance;
        int messageNoTitleTextAppearance;
        int buttonTextAppearance;

        Builder() {
        }

        public Builder setTitleTextAppearance(int titleTextAppearance) {
            this.titleTextAppearance = titleTextAppearance;
            return this;
        }

        public Builder setMessageTextAppearance(int messageTextAppearance) {
            this.messageTextAppearance = messageTextAppearance;
            return this;
        }

        public Builder setMessageNoTitleTextAppearance(int messageNoTitleTextAppearance) {
            this.messageNoTitleTextAppearance = messageNoTitleTextAppearance;
            return this;
        }

        public Builder setButtonTextAppearance(int buttonTextAppearance) {
            this.buttonTextAppearance = buttonTextAppearance;
            return this;
        }

        public MessageParameters build() {
            MessageParameters params = new MessageParameters();
            params.titleTextAppearance = this.titleTextAppearance;
            params.messageTextAppearance = this.messageTextAppearance;
            params.messageNoTitleTextAppearance = this.messageNoTitleTextAppearance;
            params.buttonTextAppearance = this.buttonTextAppearance;
            return params;
        }
    }
}
