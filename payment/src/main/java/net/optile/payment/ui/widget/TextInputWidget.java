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

package net.optile.payment.ui.widget;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.validation.ValidationResult;

/**
 * Class for handling text input
 */
public final class TextInputWidget extends FormWidget {

    private final static String NUMERIC_DIGITS = "0123456789 -";
    private final static int INTEGER_MAXLENGTH = 4;

    final InputElement element;

    final TextInputEditText input;

    final TextInputLayout layout;

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public TextInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;

        layout = rootView.findViewById(R.id.layout_value);
        input = rootView.findViewById(R.id.input_value);

        layout.setHintAnimationEnabled(false);
        layout.setHint(element.getLabel());
        layout.setHintAnimationEnabled(true);

        initInputEditText();
    }

    public boolean validate() {
        input.clearFocus();
        ValidationResult result = presenter.validate(name, getNormalizedValue(), null);

        if (result == null) {
            return false;
        }
        if (result.isError()) {
            setValidation(VALIDATION_ERROR, true, result.getMessage());
            return false;
        }
        setValidation(VALIDATION_OK, false, null);
        return true;
    }

    public void putValue(Charge charge) throws PaymentException {
        String val = getNormalizedValue();

        if (!TextUtils.isEmpty(val)) {
            charge.putValue(element.getName(), val);
        }
    }

    public boolean setLastImeOptionsWidget() {
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    void setInputType(int type) {
        input.setInputType(type);
    }

    private void onKeyboardDone() {
        validate();
        presenter.onKeyboardDone();
    }

    private void initInputEditText() {

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                handleOnFocusChange(hasFocus);
            }
        });
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onKeyboardDone();
                }
                return false;
            }
        });
        switch (element.getType()) {
            case InputElementType.NUMERIC:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setKeyListener(DigitsKeyListener.getInstance(NUMERIC_DIGITS));
                break;
            case InputElementType.INTEGER:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(INTEGER_MAXLENGTH);
                input.setFilters(filters);
        }
    }

    private String getNormalizedValue() {
        String val = input.getText().toString().trim();

        switch (name) {
            case PaymentInputType.ACCOUNT_NUMBER:
            case PaymentInputType.VERIFICATION_CODE:
            case PaymentInputType.BANK_CODE:
            case PaymentInputType.IBAN:
            case PaymentInputType.BIC:
                return val.replaceAll("[\\s|-]", "");
        }
        return val;
    }

    private void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setValidation(VALIDATION_UNKNOWN, false, null);
        } else {
            validate();
        }
    }

    private void setValidation(int state, boolean errorEnabled, String message) {
        setState(state);
        layout.setErrorEnabled(errorEnabled);
        layout.setError(message);
    }
}
