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

import java.util.HashMap;
import java.util.Map;
import android.text.TextUtils;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.R;

/**
 * Class for holding the CheckBoxWidgetParameters for the PaymentTheme
 */
public final class CheckBoxWidgetParameters {

    private int themeResId;
    
    CheckBoxWidgetParameters() {
    }
    
    public static Builder createBuilder() {
        return new Builder();
    }

    public int getThemeResId() {
        return themeResId;
    }

    public final static class Builder {
        int themeResId = R.style.PaymentThemeCheckBox;
        
        public Builder() {
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }
        
        public CheckBoxWidgetParameters build() {
            CheckBoxWidgetParameters params = new CheckBoxWidgetParameters();
            params.themeResId = this.themeResId;
            return params;
        }
    }
}

