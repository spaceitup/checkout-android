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
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.validation.ValidationResult;
import android.view.Gravity;

/**
 * Class for handling text input
 */
public final class TextInputWidget extends InputLayoutWidget {

    private final static String NUMERIC_DIGITS = "0123456789 -";
    private final static int INTEGER_MAXLENGTH = 4;
    private final InputElement element;
    
    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public TextInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView, element.getLabel());
        this.element = element;

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleOnKeyboardDone();
                }
                return false;
            }
        });
        
        switch (element.getType()) {
        case InputElementType.NUMERIC:
            setInputType(InputType.TYPE_CLASS_NUMBER, NUMERIC_DIGITS);
            break;
        case InputElementType.INTEGER:
            setInputType(InputType.TYPE_CLASS_NUMBER, null);
            setMaxLength(INTEGER_MAXLENGTH);
            setLayoutWidth(WEIGHT_REDUCED);
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
        if (input.hasFocus()) {
            input.clearFocus();
        }
        return validated;
    }

    public void putValue(Charge charge) throws PaymentException {
        String val = getNormalizedValue();

        if (!TextUtils.isEmpty(val)) {
            charge.putValue(element.getName(), val);
        }
    }

    void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setValidation(VALIDATION_UNKNOWN, false, null);
        } else if (state == VALIDATION_UNKNOWN) {
            validate();
        }
    }

    void handleOnKeyboardDone() {
        input.clearFocus();
        presenter.hideKeyboard();
    }
}
