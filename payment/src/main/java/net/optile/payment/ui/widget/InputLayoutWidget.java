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
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
abstract class InputLayoutWidget extends FormWidget {

    final static float WEIGHT_REDUCED = 0.65f;
    final static float WEIGHT_FULL = 1.0f;

    final TextInputEditText input;
    final TextInputLayout layout;

    String label;

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to apply
     */
    InputLayoutWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        this.layout = rootView.findViewById(R.id.textinputlayout);
        this.input = rootView.findViewById(R.id.textinputedittext);
        
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                handleOnFocusChange(hasFocus);
            }
        });
    }

    public void setLabel(String label) {
        this.label = label;
        layout.setHintAnimationEnabled(false);
        layout.setHint(label);
        layout.setHintAnimationEnabled(true);
    }

    public boolean setLastImeOptionsWidget() {
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }
    
    void handleOnFocusChange(boolean hasFocus) {
    }

    void setLayoutWidth(float weight) {
        layout.setLayoutParams(new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            weight));
    }

    void setMaxLength(int length) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(length);
        input.setFilters(filters);
    }

    void setInputType(int type, String digits) {
        input.setInputType(type);

        if (!TextUtils.isEmpty(digits)) {
            input.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    String getNormalizedValue() {
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

    void setValidation(int state, boolean errorEnabled, String message) {
        setState(state);
        layout.setErrorEnabled(errorEnabled);
        layout.setError(message);
    }
}
