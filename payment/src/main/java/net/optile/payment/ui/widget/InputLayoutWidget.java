/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
abstract class InputLayoutWidget extends FormWidget {

    final static float REDUCED_PORTRAIT_TEXT = 0.65f;
    final static float REDUCED_PORTRAIT_HINT = 0.35f;
    final static float REDUCED_LANDSCAPE_TEXT = 0.5f;
    final static float REDUCED_LANDSCAPE_HINT = 0.5f;

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

        hintImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onHintClicked(name);
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

    public void setHint(boolean visible, int hintDrawable) {

        if (visible) {
            hintLayout.setVisibility(View.VISIBLE);
            hintImage.setImageResource(hintDrawable);
        } else {
            hintLayout.setVisibility(View.GONE);
        }
    }

    void handleOnFocusChange(boolean hasFocus) {
    }

    void setReducedView() {
        boolean landscape = PaymentUtils.isLandscape(rootView.getContext());
        setReducedWidth(textLayout, landscape ? REDUCED_LANDSCAPE_TEXT : REDUCED_PORTRAIT_TEXT);
        setReducedWidth(hintLayout, landscape ? REDUCED_LANDSCAPE_HINT : REDUCED_PORTRAIT_HINT);
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

    private void setReducedWidth(View view, float weight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.weight = weight;
        params.width = 0;
        view.setLayoutParams(params);
    }
}
