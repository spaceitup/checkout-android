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
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
abstract class InputLayoutWidget extends FormWidget {

    final static float WEIGHT_REDUCED_TEXT = 0.65f;
    final static float WEIGHT_REDUCED_HINT = 0.35f;

    final TextInputEditText textInput;
    final TextInputLayout textLayout;

    final View hintLayout;
    final ImageView hintImage;
    
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
        this.textLayout = rootView.findViewById(R.id.textinputlayout);
        this.textInput = rootView.findViewById(R.id.textinputedittext);
        this.hintLayout = rootView.findViewById(R.id.layout_hint);
        this.hintImage = rootView.findViewById(R.id.image_hint);

        textInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                handleOnFocusChange(hasFocus);
            }
        });
    }

    public void setLabel(String label) {
        this.label = label;
        textLayout.setHintAnimationEnabled(false);
        textLayout.setHint(label);
        textLayout.setHintAnimationEnabled(true);
    }

    public boolean setLastImeOptionsWidget() {
        textInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    public void clearInputErrors() {
        if (TextUtils.isEmpty(getNormalizedValue())) {
            setValidation(VALIDATION_UNKNOWN, false, null);
        }
    }

    public void showHint(boolean val) {
        if (hintLayout == null) {
            return;
        }
        hintLayout.setVisibility(val ? View.VISIBLE : View.GONE);
    }
        
    void handleOnFocusChange(boolean hasFocus) {
    }

    void setReducedView() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)textLayout.getLayoutParams();
        params.weight = WEIGHT_REDUCED_TEXT;
        params.width = 0;
        textLayout.setLayoutParams(params);

        if (hintLayout != null) {
            params = (LinearLayout.LayoutParams)hintLayout.getLayoutParams();
            params.weight = WEIGHT_REDUCED_HINT;
            params.width = 0;
            hintLayout.setLayoutParams(params);
        }
    }

    void setMaxLength(int length) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(length);
        textInput.setFilters(filters);
    }

    void setInputType(int type, String digits) {
        textInput.setInputType(type);

        if (!TextUtils.isEmpty(digits)) {
            textInput.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    String getNormalizedValue() {
        String val = textInput.getText().toString().trim();

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
        textLayout.setErrorEnabled(errorEnabled);
        textLayout.setError(message);
    }
}
