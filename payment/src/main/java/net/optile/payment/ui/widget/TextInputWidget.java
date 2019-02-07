/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling text input
 */
public final class TextInputWidget extends InputLayoutWidget {

    private final static String NUMERIC_DIGITS = "0123456789 -";

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
                presenter.onTextInputChanged(name, getNormalizedValue());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void setInputType(String type) {

        switch (type) {
            case InputElementType.NUMERIC:
                setInputType(InputType.TYPE_CLASS_NUMBER, NUMERIC_DIGITS);
                break;
            case InputElementType.INTEGER:
                setInputType(InputType.TYPE_CLASS_NUMBER, null);
                setReducedView();
        }
    }

    public boolean validate() {
        ValidationResult result = presenter.validate(name, getNormalizedValue(), null);

        if (result == null) {
            return false;
        }
        boolean validated = false;
        if (result.isError()) {
            setValidation(VALIDATION_ERROR, true, result.getMessage());
        } else {
            setValidation(VALIDATION_OK, false, null);
            validated = true;
        }
        if (textInput.hasFocus()) {
            textInput.clearFocus();
        }
        return validated;
    }

    public void putValue(Operation operation) throws PaymentException {
        String val = getNormalizedValue();

        if (!TextUtils.isEmpty(val)) {
            operation.putValue(name, val);
        }
    }

    void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setValidation(VALIDATION_UNKNOWN, false, null);
        } else if (state == VALIDATION_UNKNOWN && !TextUtils.isEmpty(getNormalizedValue())) {
            validate();
        }
    }

    void handleOnKeyboardDone() {
        textInput.clearFocus();
        presenter.hideKeyboard();
    }
}
