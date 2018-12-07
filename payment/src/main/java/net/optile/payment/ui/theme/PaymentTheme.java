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

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme {

    private PageParameters pageParameters;
    private IconParameters iconParameters;
    private ButtonParameters buttonParameters;
    private CheckBoxParameters checkBoxParameters;
    private DateParameters dateParameters;
    private ListParameters listParameters;
    private MessageParameters messageParameters;
    private TextInputParameters textInputParameters;

    private PaymentTheme() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PaymentTheme createDefault() {
        return createBuilder().
            setPageParameters(PageParameters.createDefault()).
            setIconParameters(IconParameters.createDefault()).
            setButtonParameters(ButtonParameters.createDefault()).
            setCheckBoxParameters(CheckBoxParameters.createDefault()).
            setDateParameters(DateParameters.createDefault()).
            setListParameters(ListParameters.createDefault()).
            setMessageParameters(MessageParameters.createDefault()).
            setTextInputParameters(TextInputParameters.createDefault()).
            build();
    }

    public PageParameters getPageParameters() {
        return pageParameters;
    }

    public IconParameters getIconParameters() {
        return iconParameters;
    }

    public ButtonParameters getButtonParameters() {
        return buttonParameters;
    }

    public CheckBoxParameters getCheckBoxParameters() {
        return checkBoxParameters;
    }

    public DateParameters getDateParameters() {
        return dateParameters;
    }

    public ListParameters getListParameters() {
        return listParameters;
    }

    public MessageParameters getMessageParameters() {
        return messageParameters;
    }

    public TextInputParameters getTextInputParameters() {
        return textInputParameters;
    }

    public static final class Builder {
        PageParameters pageParameters;
        IconParameters iconParameters;
        ButtonParameters buttonParameters;
        CheckBoxParameters checkBoxParameters;
        DateParameters dateParameters;
        ListParameters listParameters;
        MessageParameters messageParameters;
        TextInputParameters textInputParameters;

        Builder() {
        }

        public Builder setPageParameters(PageParameters pageParameters) {
            this.pageParameters = pageParameters;
            return this;
        }

        public Builder setIconParameters(IconParameters iconParameters) {
            this.iconParameters = iconParameters;
            return this;
        }

        public Builder setButtonParameters(ButtonParameters buttonParameters) {
            this.buttonParameters = buttonParameters;
            return this;
        }

        public Builder setCheckBoxParameters(CheckBoxParameters checkBoxParameters) {
            this.checkBoxParameters = checkBoxParameters;
            return this;
        }

        public Builder setDateParameters(DateParameters dateParameters) {
            this.dateParameters = dateParameters;
            return this;
        }

        public Builder setListParameters(ListParameters listParameters) {
            this.listParameters = listParameters;
            return this;
        }

        public Builder setMessageParameters(MessageParameters messageParameters) {
            this.messageParameters = messageParameters;
            return this;
        }

        public Builder setTextInputParameters(TextInputParameters textInputParameters) {
            this.textInputParameters = textInputParameters;
            return this;
        }

        public PaymentTheme build() {
            PaymentTheme theme = new PaymentTheme();

            if (pageParameters == null) {
                pageParameters = PageParameters.createBuilder().build();
            }
            theme.pageParameters = pageParameters;

            if (iconParameters == null) {
                iconParameters = IconParameters.createBuilder().build();
            }
            theme.iconParameters = iconParameters;

            if (buttonParameters == null) {
                buttonParameters = ButtonParameters.createBuilder().build();
            }
            theme.buttonParameters = buttonParameters;

            if (checkBoxParameters == null) {
                checkBoxParameters = CheckBoxParameters.createBuilder().build();
            }
            theme.checkBoxParameters = checkBoxParameters;

            if (dateParameters == null) {
                dateParameters = DateParameters.createBuilder().build();
            }
            theme.dateParameters = dateParameters;

            if (listParameters == null) {
                listParameters = ListParameters.createBuilder().build();
            }
            theme.listParameters = listParameters;

            if (messageParameters == null) {
                messageParameters = MessageParameters.createBuilder().build();
            }
            theme.messageParameters = messageParameters;

            if (textInputParameters == null) {
                textInputParameters = TextInputParameters.createBuilder().build();
            }
            theme.textInputParameters = textInputParameters;
            return theme;
        }
    }
}

