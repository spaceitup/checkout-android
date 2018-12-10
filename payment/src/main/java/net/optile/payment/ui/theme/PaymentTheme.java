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
    private WidgetParameters widgetParameters;
    private DialogParameters dialogParameters;

    private PaymentTheme() {
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PaymentTheme createDefault() {
        return createBuilder().
            setPageParameters(PageParameters.createDefault()).
            setWidgetParameters(WidgetParameters.createDefault()).
            setDialogParameters(DialogParameters.createDefault()).
            build();
    }

    public PageParameters getPageParameters() {
        return pageParameters;
    }

    public WidgetParameters getWidgetParameters() {
        return widgetParameters;
    }

    public DialogParameters getDialogParameters() {
        return dialogParameters;
    }

    public static final class Builder {
        PageParameters pageParameters;
        WidgetParameters widgetParameters;
        DialogParameters dialogParameters;

        Builder() {
        }

        public Builder setPageParameters(PageParameters pageParameters) {
            this.pageParameters = pageParameters;
            return this;
        }

        public Builder setWidgetParameters(WidgetParameters widgetParameters) {
            this.widgetParameters = widgetParameters;
            return this;
        }

        public Builder setDialogParameters(DialogParameters dialogParameters) {
            this.dialogParameters = dialogParameters;
            return this;
        }

        public PaymentTheme build() {
            PaymentTheme theme = new PaymentTheme();

            if (pageParameters == null) {
                pageParameters = PageParameters.createBuilder().build();
            }
            theme.pageParameters = pageParameters;

            if (widgetParameters == null) {
                widgetParameters = WidgetParameters.createBuilder().build();
            }
            theme.widgetParameters = widgetParameters;

            if (dialogParameters == null) {
                dialogParameters = DialogParameters.createBuilder().build();
            }
            theme.dialogParameters = dialogParameters;
            return theme;
        }
    }
}

