/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.view.View;
import net.optile.payment.localization.Localization;
import net.optile.payment.localization.LocalizationKey;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.widget.input.EditTextInputModeFactory;

/**
 * Widget for showing the verification code input
 */
public final class VerificationCodeWidget extends InputLayoutWidget {

    /**
     * Construct a new VerificationCodeWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme the PaymentTheme to apply
     */
    public VerificationCodeWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
    }

    /** 
     * Bind this verification code widget to the InputElement.
     * 
     * @param code of the payment network this widget belongs to
     * @param element to bind this widget to
     * @param selected indicates that this widget is bound to a network selected by the user
     */
    public void onBind(String code, InputElement element, boolean selected) {
        int maxLength = presenter.getMaxLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();

        String key = selected ? LocalizationKey.VERIFICATIONCODE_SPECIFIC_PLACEHOLDER :
            LocalizationKey.VERIFICATIONCODE_GENERIC_PLACEHOLDER;
        setLabel(Localization.translate(code, key));
        setHelperText(Localization.translateAccountLabel(code, name));
    }
}
