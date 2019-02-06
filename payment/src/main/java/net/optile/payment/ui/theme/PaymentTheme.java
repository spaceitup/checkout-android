/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

/**
 * Class to hold the theme settings of the payment screens in the Android SDK
 */
public final class PaymentTheme {

    private final PageParameters pageParameters;
    private final WidgetParameters widgetParameters;
    private final DialogParameters dialogParameters;
    private final ProgressParameters progressParameters;

    private PaymentTheme(Builder builder) {
        this.pageParameters = builder.pageParameters;
        this.widgetParameters = builder.widgetParameters;
        this.dialogParameters = builder.dialogParameters;
        this.progressParameters = builder.progressParameters;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static PaymentTheme createDefault() {
        return createBuilder().
            setPageParameters(PageParameters.createDefault()).
            setWidgetParameters(WidgetParameters.createDefault()).
            setDialogParameters(DialogParameters.createDefault()).
            setProgressParameters(ProgressParameters.createDefault()).
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

    public ProgressParameters getProgressParameters() {
        return progressParameters;
    }

    public static final class Builder {
        PageParameters pageParameters;
        WidgetParameters widgetParameters;
        DialogParameters dialogParameters;
        ProgressParameters progressParameters;

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

        public Builder setProgressParameters(ProgressParameters progressParameters) {
            this.progressParameters = progressParameters;
            return this;
        }

        public PaymentTheme build() {

            if (pageParameters == null) {
                pageParameters = PageParameters.createBuilder().build();
            }
            if (widgetParameters == null) {
                widgetParameters = WidgetParameters.createBuilder().build();
            }
            if (dialogParameters == null) {
                dialogParameters = DialogParameters.createBuilder().build();
            }
            if (progressParameters == null) {
                progressParameters = ProgressParameters.createBuilder().build();
            }
            return new PaymentTheme(this);
        }
    }
}

