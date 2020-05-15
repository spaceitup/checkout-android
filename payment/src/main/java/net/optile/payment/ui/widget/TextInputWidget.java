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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.widget.input.TextInputMode;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling text input
 */
public class TextInputWidget extends InputLayoutWidget {
    private TextInputMode mode;

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to apply to this widget
     */
    public TextInputWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        textInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleOnKeyboardDone();
                }
                return false;
            }
        });
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

    public void setTextInputMode(TextInputMode mode) {
        if (this.mode != null) {
            this.mode.reset();
        }
        this.mode = mode;
        mode.apply(textInput);
    }

    String getValue() {
        String val = super.getValue();
        return mode != null ? mode.normalize(val) : val;
    }

    void handleOnFocusChange(boolean hasFocus) {

        if (hasFocus) {
            setInputLayoutState(VALIDATION_UNKNOWN, false, null);
        } else if (state == VALIDATION_UNKNOWN && !TextUtils.isEmpty(getValue())) {
            validate();
        }
    }

    void handleOnKeyboardDone() {
        textInput.clearFocus();
        presenter.hideKeyboard();
    }
}
