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

import android.widget.LinearLayout;
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
import net.optile.payment.model.InputElementType;
import net.optile.payment.validation.ValidationResult;
import android.view.Gravity;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
abstract class InputLayoutWidget extends FormWidget {

    final static float WEIGHT_REDUCED = 0.65f;    
    final static float WEIGHT_FULL = 1.0f;        
    
    final TextInputEditText input;

    final TextInputLayout layout;

    final String label;
    
    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param label the hint / label of the input layout
     */
    InputLayoutWidget(String name, View rootView, String label) {
        super(name, rootView);
        this.layout = rootView.findViewById(R.id.layout_value);
        this.input = rootView.findViewById(R.id.input_value);
        this.label = label;
        
        layout.setHintAnimationEnabled(false);
        layout.setHint(label);
        layout.setHintAnimationEnabled(true);
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
