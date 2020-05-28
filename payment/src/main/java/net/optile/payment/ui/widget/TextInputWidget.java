/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.PaymentTheme;
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
     * @param theme PaymentTheme to apply to this widget
     */
    public TextInputWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
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
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        ValidationResult result = presenter.validate(name, getValue(), null);
        return setValidationResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        String val = getValue();
        if (!TextUtils.isEmpty(val)) {
            operation.putValue(name, val);
        }
    }

    /** 
     * Set the InputElement into this TextInputWidget
     * 
     * @param maxLength the maxLength hint for the TextInput 
     * @param element to be set in this widget
     */
    public void setInputElement(int maxLength, InputElement element) {
        EditTextInputMode mode = EditTextInputModeFactory.createMode(maxLength, element);
        setTextInputMode(mode);
        setValidation();
    }

    String getValue() {
        String val = super.getValue();
        return mode != null ? mode.normalize(val) : val;
    }
}
