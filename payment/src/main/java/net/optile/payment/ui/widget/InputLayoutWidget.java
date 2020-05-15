/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.widget.input.TextInputMode;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * Base class for widgets using the TextInputLayout and TextInputEditText
 */
public abstract class InputLayoutWidget extends FormWidget {

    final static float REDUCED_PORTRAIT_TEXT = 0.65f;
    final static float REDUCED_PORTRAIT_HINT = 0.35f;
    final static float REDUCED_LANDSCAPE_TEXT = 0.5f;
    final static float REDUCED_LANDSCAPE_HINT = 0.5f;

    final TextInputEditText textInput;
    final TextInputLayout textLayout;

    final View hintLayout;
    final ImageView hintImage;

    TextInputMode mode;
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

        textInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleOnKeyboardDone();
                }
                return false;
            }
        });
        
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setLastImeOptionsWidget() {
        textInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFocus() {
        if (textInput.hasFocus()) {
            textInput.clearFocus();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValidation() {

        if (textInput.hasFocus() || TextUtils.isEmpty(getValue())) {
            setInputLayoutState(VALIDATION_UNKNOWN, false, null);
            return;
        }
        validate();
    }

    public void setHint(boolean visible) {
        if (visible) {
            hintLayout.setVisibility(View.VISIBLE);
        } else {
            hintLayout.setVisibility(View.GONE);
        }
    }

    public void setReducedView() {
        boolean landscape = PaymentUtils.isLandscape(rootView.getContext());
        setReducedWidth(textLayout, landscape ? REDUCED_LANDSCAPE_TEXT : REDUCED_PORTRAIT_TEXT);
        setReducedWidth(hintLayout, landscape ? REDUCED_LANDSCAPE_HINT : REDUCED_PORTRAIT_HINT);
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

    void setTextInputMode(TextInputMode mode) {
        if (this.mode != null) {
            this.mode.reset();
        }
        this.mode = mode;
        mode.apply(textInput);
    }
    
    String getValue() {
        CharSequence cs = textInput.getText();
        return cs != null ? cs.toString().trim() : "";
    }

    boolean setValidationResult(ValidationResult result) {
        if (result == null) {
            return false;
        }
        if (result.isError()) {
            setInputLayoutState(VALIDATION_ERROR, true, result.getMessage());
            return false;
        }
        setInputLayoutState(VALIDATION_OK, false, null);
        return true;
    }

    void setInputLayoutState(int state, boolean errorEnabled, String message) {
        setValidationState(state);
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
