/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget;

import android.view.View;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.localization.LocalizationKey;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.ui.widget.input.EditTextInputModeFactory;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM;

/**
 * Widget for showing the verification code input
 */
public final class VerificationCodeWidget extends InputLayoutWidget {

    /**
     * Construct a new VerificationCodeWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public VerificationCodeWidget(String name, View rootView) {
        super(name, rootView);
        setEndIcon(END_ICON_CUSTOM, R.drawable.ic_tooltip);
    }

    /**
     * Bind this verification code widget to the InputElement.
     *
     * @param code of the payment network this widget belongs to
     * @param element to bind this widget to
     */
    public void onBind(String code, InputElement element) {
        int maxLength = presenter.getMaxLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translate(code, LocalizationKey.VERIFICATIONCODE_SPECIFIC_PLACEHOLDER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void handleOnEndIconClicked() {
        presenter.onHintClicked(name);
    }
}
