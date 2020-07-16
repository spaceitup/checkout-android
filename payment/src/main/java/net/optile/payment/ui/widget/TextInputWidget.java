/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.widget.input.EditTextInputMode;
import net.optile.payment.ui.widget.input.EditTextInputModeFactory;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling text input
 */
public final class TextInputWidget extends InputLayoutWidget {

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public TextInputWidget(String name, View rootView) {
        super(name, rootView);
        textInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextInputChanged(name, getValue());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Set the InputElement into this TextInputWidget
     *
     * @param code of the network
     * @param element to be set in this widget
     */
    public void onBind(String code, InputElement element) {
        int maxLength = presenter.getMaxLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translateAccountPlaceholder(code, name));        
    }
}
