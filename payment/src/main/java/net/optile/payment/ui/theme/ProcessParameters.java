/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

import android.support.annotation.AnyRes;
import android.support.annotation.StyleRes;
import net.optile.payment.R;

/**
 * Class for holding the PaymentPage theme parameters.
 * These parameters may be used to theme the PaymentPage UI elements and text appearances.
 */
public final class ProcessParameters {

    private final int pageTheme;

    private ProcessParameters(Builder builder) {
        this.pageTheme = builder.pageTheme;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static ProcessParameters createDefault() {
        return createBuilder().
            setPageTheme(R.style.PaymentThemeNoActionBar_ProcessPayment).
            build();
    }

    public int getPageTheme() {
        return pageTheme;
    }

    public final static class Builder {
        int pageTheme;

        Builder() {
        }

        public Builder setPageTheme(@StyleRes int pageTheme) {
            this.pageTheme = pageTheme;
            return this;
        }

        public ProcessParameters build() {
            return new ProcessParameters(this);
        }
    }
}
